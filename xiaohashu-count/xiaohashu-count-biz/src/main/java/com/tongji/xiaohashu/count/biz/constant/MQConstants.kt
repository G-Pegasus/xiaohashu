package com.tongji.xiaohashu.count.biz.constant

object MQConstants {
    /**
     * Topic: 关注数计数
     */
    const val TOPIC_COUNT_FOLLOWING = "CountFollowingTopic"

    /**
     * Topic: 粉丝数计数
     */
    const val TOPIC_COUNT_FANS = "CountFansTopic"

    /**
     * Topic: 粉丝数计数入库
     */
    const val TOPIC_COUNT_FANS_2_DB = "CountFans2DBTopic"

    /**
     * Topic: 关注数计数入库
     */
    const val TOPIC_COUNT_FOLLOWING_2_DB = "CountFollowing2DBTopic"

    /**
     * Topic: 计数 - 笔记点赞数
     */
    const val TOPIC_COUNT_NOTE_LIKE = "CountNoteLikeTopic"

    /**
     * Topic: 计数 - 笔记点赞数落库
     */
    const val TOPIC_COUNT_NOTE_LIKE_2_DB = "CountNoteLike2DBTTopic"

    /**
     * Topic: 计数 - 笔记收藏数
     */
    const val TOPIC_COUNT_NOTE_COLLECT = "CountNoteCollectTopic"

    /**
     * Topic: 计数 - 笔记收藏数落库
     */
    const val TOPIC_COUNT_NOTE_COLLECT_2_DB = "CountNoteCollect2DBTTopic"

    /**
     * Topic: 笔记操作（发布、删除）
     */
    const val TOPIC_NOTE_OPERATE: String = "NoteOperateTopic"

    /**
     * Tag 标签：笔记发布
     */
    const val TAG_NOTE_PUBLISH: String = "publishNote"

    /**
     * Tag 标签：笔记删除
     */
    const val TAG_NOTE_DELETE: String = "deleteNote"
}