package com.tongji.xiaohashu.note.biz.utils;

import com.github.benmanes.caffeine.cache.*;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author tongji
 * @time 2025/4/17 13:38
 * @description 热键的缓存策略
 */
public class HotKeyAwareCacheManager<K, V> {
    private final Cache<K, V> cache;
    private final ConcurrentMap<K, AccessStat> accessMap = new ConcurrentHashMap<>();
    private final Duration windowDuration = Duration.ofMinutes(5);

    public HotKeyAwareCacheManager(long maxSize) {
        this.cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<K, V>() {
                    @Override
                    public long expireAfterCreate(@NotNull K key, @NotNull V value, long currentTime) {
                        return calculateDynamicNanos(key);
                    }

                    @Override
                    public long expireAfterUpdate(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
                        return calculateDynamicNanos(key);
                    }

                    @Override
                    public long expireAfterRead(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
                        if (getAccessCountWithinWindow(key) >= 300) {
                            return calculateDynamicNanos(key);
                        }
                        return currentDuration;
                    }
                })
                .maximumSize(maxSize)
                .build();

        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
        cleaner.scheduleAtFixedRate(this::cleanOldStats, 1, 1, TimeUnit.MINUTES);
    }

    public V get(K key, Callable<V> redisLoader) {
        // 热度统计
        AccessStat stat = accessMap.computeIfAbsent(key, k -> new AccessStat());
        stat.recordAccess(System.currentTimeMillis());

        // 先查本地缓存
        V val = cache.getIfPresent(key);
        if (val != null) return val;

        try {
            // 没命中从 Redis 中取
            val = redisLoader.call();
            cache.put(key, val);
            return val;
        } catch (Exception e) {
            throw new RuntimeException("加载数据失败: " + key, e);
        }
    }

    public void put(K key, V value) {
        cache.put(key, value);
        accessMap.computeIfAbsent(key, k -> new AccessStat()).recordAccess(System.currentTimeMillis());
    }

    public void invalidate(K key) {
        cache.invalidate(key);
        accessMap.remove(key);
    }

    public void invalidateAll() {
        cache.invalidateAll();
        accessMap.clear();
    }

    private long calculateDynamicNanos(K key) {
        int recentCount = getAccessCountWithinWindow(key);
        if (recentCount > 1000) return TimeUnit.MINUTES.toNanos(30);
        if (recentCount > 500) return TimeUnit.MINUTES.toNanos(10);
        if (recentCount > 200) return TimeUnit.MINUTES.toNanos(5);
        return TimeUnit.MINUTES.toNanos(1);
    }

    private int getAccessCountWithinWindow(K key) {
        AccessStat stat = accessMap.get(key);
        if (stat == null) return 0;
        return stat.getAccessCountSince(System.currentTimeMillis() - windowDuration.toMillis());
    }

    private void cleanOldStats() {
        long now = System.currentTimeMillis();
        for (Map.Entry<K, AccessStat> entry : accessMap.entrySet()) {
            AccessStat stat = entry.getValue();
            stat.removeOlderThan(now - windowDuration.toMillis());

            if (stat.isEmpty()) {
                accessMap.remove(entry.getKey());
            }
        }
    }

    private static class AccessStat {
        private final Deque<Long> accessTimes = new ConcurrentLinkedDeque<>();

        void recordAccess(long timestamp) {
            accessTimes.addLast(timestamp);
        }

        void removeOlderThan(long threshold) {
            while (!accessTimes.isEmpty() && accessTimes.peekFirst() < threshold) {
                accessTimes.pollFirst();
            }
        }

        int getAccessCountSince(long sinceTimestamp) {
            removeOlderThan(sinceTimestamp);
            return accessTimes.size();
        }

        boolean isEmpty() {
            return accessTimes.isEmpty();
        }
    }
}
