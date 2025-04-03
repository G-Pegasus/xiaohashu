package com.tongji.xiaohashu.count.biz.domain.mapper;

import com.tongji.xiaohashu.count.biz.domain.dataobject.UserCountDO;
import org.apache.ibatis.annotations.Param;

public interface UserCountDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCountDO row);

    int insertSelective(UserCountDO row);

    UserCountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCountDO row);

    int updateByPrimaryKey(UserCountDO row);

    // 添加或更新粉丝总数
    int insertOrUpdateFansTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);

    // 添加或更新关注总数
    int insertOrUpdateFollowingTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);

    // 添加记录或更新笔记点赞数
    int insertOrUpdateLikeTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);

    // 添加记录或更新笔记收藏数
    int insertOrUpdateCollectTotalByUserId(@Param("count") Integer count, @Param("userId") Long userId);

    // 添加记录或更新笔记发布数
    int insertOrUpdateNoteTotalByUserId(@Param("count") Long count, @Param("userId") Long userId);
}