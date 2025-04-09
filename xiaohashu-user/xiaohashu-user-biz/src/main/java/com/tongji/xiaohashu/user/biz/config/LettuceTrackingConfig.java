package com.tongji.xiaohashu.user.biz.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tongji.xiaohashu.user.dto.resp.FindUserByIdRspDTO;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.output.StatusOutput;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author tongji
 * @time 2025/4/9 17:05
 * @description Redis lettuce 客户端
 */
@Configuration
@Slf4j
public class LettuceTrackingConfig {
    @Bean
    public RedisClient redisClient() {
        RedisURI redisUri = RedisURI.Builder.redis("localhost", 6379)
                .withPassword("liutongji8512".toCharArray())
                .build();

        return RedisClient.create(redisUri);
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisConnection<String, String> statefulRedisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public Cache<Long, FindUserByIdRspDTO> createLocalCache() {
        return Caffeine.newBuilder()
                .initialCapacity(10000)
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.HOURS) // 设置缓存条目在写入后 1 小时过期
                .build();
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        // 开启 Client Tracking（BCAST 模式，全键通知）
        connection.sync().dispatch(CommandType.CLIENT, new StatusOutput<>(StringCodec.UTF8), new CommandArgs<>(StringCodec.UTF8)
                .add("TRACKING").add("ON").add("BCAST").add("PREFIX").add("user:info:")); // 监听 user: 前缀的 key 变化

        return connection.sync();
    }
}
