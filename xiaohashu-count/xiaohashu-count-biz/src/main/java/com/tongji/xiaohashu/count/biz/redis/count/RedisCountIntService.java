package com.tongji.xiaohashu.count.biz.redis.count;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author tongji
 * @time 2025/5/20 13:38
 * @description Redis 存取服务封装
 */
@Service
public class RedisCountIntService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CountSchema schema;

    public RedisCountIntService(RedisTemplate<String, Object> redisTemplate, CountSchema schema) {
        this.redisTemplate = redisTemplate;
        this.schema = schema;
    }

    public void write(String realKey, CountInt count) {
        redisTemplate.opsForValue().set(realKey, count.toBytes());
    }

    public CountInt read(String keyPattern, String realKey) {
        byte[] data = (byte[]) redisTemplate.opsForValue().get(realKey);

        if (data == null) {
            return new CountInt(schema.getFieldCount(keyPattern));
        }

        return CountInt.fromBytes(data, schema.getFieldCount(keyPattern));
    }

    // keyPattern = count_note, realKey = count_note_{%d}, field = like, delta = 100
    public void incr(String keyPattern, String realKey, String field, long delta) {
        CountInt count = read(keyPattern, realKey);
        int index = schema.getFieldIndex(keyPattern, field);
        count.set(index, count.get(index) + delta);
        write(realKey, count);
    }
}
