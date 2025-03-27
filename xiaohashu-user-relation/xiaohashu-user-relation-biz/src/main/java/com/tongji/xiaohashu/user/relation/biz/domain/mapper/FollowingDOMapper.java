package com.tongji.xiaohashu.user.relation.biz.domain.mapper;

import com.tongji.xiaohashu.user.relation.biz.domain.dataobject.FollowingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowingDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FollowingDO row);

    int insertSelective(FollowingDO row);

    FollowingDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FollowingDO row);

    int updateByPrimaryKey(FollowingDO row);

    List<FollowingDO> selectByUserId(Long userId);

    int deleteByUserIdAndFansUserId(@Param("userId") Long userId,
                                      @Param("unfollowUserId") Long unfollowUserId);

    long selectCountByUserId(Long userId);

    List<FollowingDO> selectPageListByUserId(@Param("userId") Long userId,
                                             @Param("offset") long offset,
                                             @Param("limit") long limit);

    /**
     * 查询关注用户列表
     */
    List<FollowingDO> selectAllByUserId(Long userId);
}