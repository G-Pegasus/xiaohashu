package com.tongji.xiaohashu.note.biz.constant

object RedisKeyConstants {
    /**
     * 笔记详情 KEY 前缀
     */
    private const val NOTE_DETAIL_KEY: String = "note:detail:"

    /**
     * 构建完整的笔记详情 KEY
     * @param noteId
     * @return
     */
    @JvmStatic
    fun buildNoteDetailKey(noteId: Long) = "$NOTE_DETAIL_KEY$noteId"

    /**
     * 布隆过滤器：用户笔记点赞
     */
    private const val BLOOM_USER_NOTE_LIKE_LIST_KEY = "bloom:note:likes:"

    /**
     * 构建完整的布隆过滤器：用户笔记点赞 KEY
     */
    @JvmStatic
    fun buildBloomUserNoteLikeListKey(userId: Long) = "$BLOOM_USER_NOTE_LIKE_LIST_KEY$userId"

    /**
     * 用户笔记点赞列表 ZSet 前缀
     */
    private const val USER_NOTE_LIKE_ZSET_KEY: String = "user:note:likes:"

    /**
     * 构建完整的用户笔记点赞列表 ZSet KEY
     */
    @JvmStatic
    fun buildUserNoteLikeZSetKey(userId: Long) = "$USER_NOTE_LIKE_ZSET_KEY$userId"
}