package com.tongji.xiaohashu.distributed.id.generator.biz.constant;

/** 
* @time 2025/3/27 10:19
* @author tongji
* @description 
*/
public class RedisKeyConstants {
    /**
     * 生成 id 前缀
     */
    public static final String LEAF_REDIS_ID_KEY_PREFIX = "leaf:";

    /**
     * 分布式锁前缀
     */
    public static final String LOCK_LEAF_KEY_PREFIX = "lock:";
}
