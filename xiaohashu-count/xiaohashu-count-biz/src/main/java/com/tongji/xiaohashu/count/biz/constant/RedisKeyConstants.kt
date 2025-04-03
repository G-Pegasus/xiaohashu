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

    /**
     * 笔记维度计数 Key 前缀
     */
    private const val COUNT_NOTE_KEY_PREFIX = "count:note:"

    /**
     * Hash Field: 笔记点赞总数
     */
    const val FIELD_LIKE_TOTAL = "likeTotal"

    /**
     * 构建笔记维度计数 Key
     */
    @JvmStatic
    fun buildCountNoteKey(noteId: Long) = "$COUNT_NOTE_KEY_PREFIX$noteId"

    /**
     * Hash Field: 笔记收藏总数
     */
    const val FIELD_COLLECT_TOTAL = "collectTotal"

    /**
     * Hash Field: 笔记发布总数
     */
    const val FIELD_NOTE_TOTAL = "noteTotal"
}