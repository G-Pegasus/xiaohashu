package com.tongji.xiaohashu.user.relation.biz.constant

object MQConstants {
    /**
     * Topic: 关注、取关共用一个
     */
    const val TOPIC_FOLLOW_OR_UNFOLLOW = "FollowUnfollowTopic"

    /**
     * 关注标签
     */
    const val TAG_FOLLOW = "Follow"

    /**
     * 取关标签
     */
    const val TAG_UNFOLLOW = "Unfollow"

    /**
     * Topic: 关注数计数
     */
    const val TOPIC_COUNT_FOLLOWING = "CountFollowingTopic"

    /**
     * Topic: 粉丝数计数
     */
    const val TOPIC_COUNT_FANS = "CountFansTopic"
}