package com.tongji.xiaohashu.count.biz.constant

/**
 * @time 2025/3/28 13:57
 * @author tongji
 * @description
 */
object RedisKeyConstants {
    /**
     * 用户维度计数 Key 前缀
     */
    private const val COUNT_USER_KEY_PREFIX = "count:user:"

    /**
     * Hash Field: 粉丝总数
     */
    const val FIELD_FANS_TOTAL = "fansTotal"

    /**
     * 构建用户维度计数 Key
     */
    @JvmStatic
    fun buildCountUserKey(userId: Long) = "$COUNT_USER_KEY_PREFIX$userId"

    /**
     * Hash Field: 关注总数
     */
    const val FIELD_FOLLOWING_TOTAL = "followingTotal"
}