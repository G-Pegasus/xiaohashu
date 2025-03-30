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
}