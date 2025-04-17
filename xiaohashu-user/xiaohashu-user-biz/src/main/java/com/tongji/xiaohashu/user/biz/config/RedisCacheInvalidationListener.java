package com.tongji.xiaohashu.user.biz.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.tongji.xiaohashu.user.dto.resp.FindUserByIdRspDTO;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.StringCodec;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tongji
 * @time 2025/4/9 20:56
 * @description
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCacheInvalidationListener {
    @Resource
    private final StatefulRedisConnection<String, String> connection;
    @Resource
    private Cache<Long, FindUserByIdRspDTO> LOCAL_CACHE;

    @PostConstruct
    public void startTrackingListener() {
        connection.addListener(pushMessage -> {
            if (pushMessage.getType().equals("invalidate")) {
                List<Object> content = pushMessage.getContent(StringCodec.UTF8::decodeKey);
                for (Object obj : content) {
                    String key = obj.toString();
                    if (key.startsWith("[user:info:")) {
                        Long userId = 9L;
                        LOCAL_CACHE.invalidate(userId);
                        log.info("收到 Redis 失效推送，已清除本地缓存：{}", key);
                    }
                }
            }
        });
    }
}
