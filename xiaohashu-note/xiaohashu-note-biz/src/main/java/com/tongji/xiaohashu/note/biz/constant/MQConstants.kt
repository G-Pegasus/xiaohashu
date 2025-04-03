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

    // 计数-笔记点赞数
    const val TOPIC_COUNT_NOTE_LIKE = "CountNoteLikeTopic"

    // Topic：点赞、取消点赞公用一个
    const val TOPIC_COLLECT_OR_UN_COLLECT = "CollectUnCollectTopic"

    // 收藏标签
    const val TAG_COLLECT = "Collect"

    // 取消收藏标签
    const val TAG_UN_COLLECT = "UnCollect"

    // 计数-笔记收藏数
    const val TOPIC_COUNT_NOTE_COLLECT = "CountNoteCollectTopic"

    // Topic 主题：延迟双删 Redis 笔记缓存
    const val TOPIC_DELAY_DELETE_NOTE_REDIS_CACHE = "DelayDeleteNoteRedisCacheTopic"

    // Topic: 笔记操作（发布、删除）
    const val TOPIC_NOTE_OPERATE = "NoteOperateTopic"

    // Tag 标签：笔记发布
    const val TAG_NOTE_PUBLISH = "publishNote"

    // Tag 标签：笔记删除
    const val TAG_NOTE_DELETE  = "deleteNote"
}