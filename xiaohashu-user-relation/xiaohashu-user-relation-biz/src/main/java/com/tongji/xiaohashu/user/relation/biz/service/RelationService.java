package com.tongji.xiaohashu.user.relation.biz.service;

import com.tongji.framework.common.response.PageResponse;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.relation.biz.model.vo.*;

/**
 * @author tongji
 * @time 2025/3/26 20:48
 * @description 关注业务
 */
public interface RelationService {
    // 关注用户
    Response<?> follow(FollowUserReqVO followUserReqVO);
    // 取关用户
    Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO);
    // 查询关注列表
    PageResponse<FindFollowingUserRspVO> findFollowingList(FindFollowingListReqVO findFollowingListReqVO);
    // 查询粉丝列表
    PageResponse<FindFansUserRspVO> findFansList(FindFansListReqVO findFansListReqVO);
}
