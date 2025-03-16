package com.tongji.xiaohashu.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * @author tongji
 * @time 2025/3/16 14:12
 * @description Redis连接
 */
@Configuration
open class RedisTemplateConfig {
    @Bean
    open fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        // 设置 RedisTemplate 的连接工厂
        redisTemplate.connectionFactory = connectionFactory

        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值，确保 key 是可读的字符串
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()

        // 使用 Jackson2JsonRedisSerializer 来序列化和反序列化 redis 的 value 值, 确保存储的是 JSON 格式
        val serializer = Jackson2JsonRedisSerializer(Any::class.java)
        redisTemplate.valueSerializer = serializer
        redisTemplate.hashValueSerializer = serializer

        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}