package com.tongji.xiaohashu.distributed.id.generator.biz.service;

import com.tongji.xiaohashu.distributed.id.generator.biz.core.IDGen;
import com.tongji.xiaohashu.distributed.id.generator.biz.core.common.Result;
import com.tongji.xiaohashu.distributed.id.generator.biz.exception.InitException;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author tongji
 * @time 2025/3/29 20:33
 * @description
 */
//@Service("RedisGenIDService")
//public class RedisGenIDService {
//    private final IDGen idGen;
//
//    public RedisGenIDService() throws InitException {
//        idGen = new RedisIDGenImpl();
//
//        if (idGen.init()) {
//            Logger logger = LoggerFactory.getLogger(SegmentService.class);
//            logger.info("Redis Service Init Successfully");
//        } else {
//            throw new InitException("Redis Service Init Fail");
//        }
//    }
//
//    public Result getId(String key) {
//        return idGen.get(key);
//    }
//}
