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
    private Cache<K, V> cache;
    private final ConcurrentMap<K, AccessStat> accessMap = new ConcurrentHashMap<>();
    private Duration windowDuration = Duration.ofMinutes(5);

    /**
     * @param maxSize 本地缓存的最大数量
     * @description 自定义 Caffeine 本地缓存的过期策略，创建或者更新时，重新计算过期时间。每次访问时，根据计数多少来决定是否对该
     * key 进行续期。并定义定时任务，每一分钟进行一次清理，清理 5 分钟以前的访问记录，如果 5 分钟内没有任何访问，即计数为 0，就将该
     * key 的数据从本地缓存和 accessMap 中清除，节省内存
     */
    public HotKeyAwareCacheManager(long maxSize) {
        caffeineInit(maxSize);

        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
        cleaner.scheduleAtFixedRate(this::cleanOldStats, 1, 1, TimeUnit.MINUTES);
    }

    public HotKeyAwareCacheManager(long maxSize, int windowSize) {
        caffeineInit(maxSize);

        this.windowDuration = Duration.ofMinutes(windowSize);

        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();
        cleaner.scheduleAtFixedRate(this::cleanOldStats, 1, 1, TimeUnit.MINUTES);
    }

    private void caffeineInit(long maxSize) {
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
    }

    public V get(K key) {
        // 热度统计
        AccessStat stat = accessMap.computeIfAbsent(key, k -> new AccessStat());
        stat.recordAccess(System.currentTimeMillis());

        // 先查本地缓存
        return cache.getIfPresent(key);
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

    /**
     * @description 根据访问计数动态计算过期时间
     */
    private long calculateDynamicNanos(K key) {
        int recentCount = getAccessCountWithinWindow(key);
        if (recentCount > 1000) return TimeUnit.MINUTES.toNanos(30);
        if (recentCount > 500) return TimeUnit.MINUTES.toNanos(10);
        if (recentCount > 200) return TimeUnit.MINUTES.toNanos(5);
        return TimeUnit.MINUTES.toNanos(1);
    }

    /**
     * @description 获得 5 分钟内访问窗口的计数
     */
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
                K key = entry.getKey();
                accessMap.remove(key);
                cache.invalidate(key);
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
