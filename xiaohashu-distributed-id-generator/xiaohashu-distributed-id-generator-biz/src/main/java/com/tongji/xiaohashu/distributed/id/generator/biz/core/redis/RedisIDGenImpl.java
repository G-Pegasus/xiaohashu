package com.tongji.xiaohashu.distributed.id.generator.biz.core.redis;

import com.tongji.xiaohashu.distributed.id.generator.biz.config.RedisTemplateConfig;
import com.tongji.xiaohashu.distributed.id.generator.biz.constant.RedisKeyConstants;
import com.tongji.xiaohashu.distributed.id.generator.biz.core.IDGen;
import com.tongji.xiaohashu.distributed.id.generator.biz.core.common.Result;
import com.tongji.xiaohashu.distributed.id.generator.biz.core.common.Status;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author tongji
 * @time 2025/3/29 19:37
 * @description
 */
//@Service("RedisGenID")
//public class RedisIDGenImpl implements IDGen {
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//    // 当前内存号段起始值
//    private long currentStart;
//    // 下一个号段阈值
//    private long nextThreshold;
//
//    @Override
//    public boolean init() {
//        Boolean exists = redisTemplate.hasKey(RedisKeyConstants.LEAF_REDIS_ID_KEY_PREFIX);
//        if (!exists) {
//            // 分布式锁防止并发初始化（Redisson实现）
//            try {
//                if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(RedisKeyConstants.LOCK_LEAF_KEY_PREFIX, 1L, 10, TimeUnit.SECONDS))) {
//                    // 从MySQL查询
//                    long maxId = getMaxIdFromDatabase();
//                    redisTemplate.opsForValue().set(RedisKeyConstants.LEAF_REDIS_ID_KEY_PREFIX, maxId + 1);
//                }
//            } finally {
//                redisTemplate.delete(RedisKeyConstants.LOCK_LEAF_KEY_PREFIX);
//            }
//        } else {
//            return false;
//        }
//        // 初始化的时候先加载一个号段
//        loadNextSegment();
//        return true;
//    }
//
//    @Override
//    public Result get(String key) {
//        // 当前号段消耗完再从 Redis 中取下一号段
//        if (currentStart >= nextThreshold) {
//            loadNextSegment();
//        }
//        long id = currentStart++;
//        sendToRocketMQ(id);
//        return new Result(id, Status.SUCCESS);
//    }
//
//    private void loadNextSegment() {
//        // 步长
//        int step = 1000;
//        Long newMax = redisTemplate.opsForValue().increment(RedisKeyConstants.LEAF_REDIS_ID_KEY_PREFIX, step);
//        if (newMax != null) {
//            currentStart = newMax - step + 1;
//            nextThreshold = newMax;
//        }
//    }
//
//    private long getMaxIdFromDatabase() {
//        // 伪代码：SELECT MAX(id) FROM users
//        return 1000L;
//    }
//
//    private void sendToRocketMQ(long id) {
//        rocketMQTemplate.asyncSend("USER_ID_PERSIST_TOPIC", id, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//
//            }
//            @Override
//            public void onException(Throwable e) {
//
//            }
//        });
//    }
//}
