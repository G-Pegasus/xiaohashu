package com.tongji.xiaohashu.note.biz.constant

/**
 * @time 2025/3/26 10:14
 * @author tongji
 * @description
 */
object MQConstants {
    // Topic 主题：删除笔记本地缓存
    const val TOPIC_DELETE_NOTE_LOCAL_CACHE = "DeleteNoteLocalCacheTopic"

    // Topic：点赞、取消点赞公用一个
    const val TOPIC_LIKE_OR_UNLIKE = "LikeUnlikeTopic"

    // 点赞标签
    const val TAG_LIKE = "Like"

    // Tag 标签：取消点赞
    const val TAG_UNLIKE = "Unlike"
}