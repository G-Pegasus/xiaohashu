package com.tongji.xiaohashu.user.relation.biz.domain.mapper;

import com.tongji.xiaohashu.user.relation.biz.domain.dataobject.FansDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FansDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FansDO row);

    int insertSelective(FansDO row);

    FansDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FansDO row);

    int updateByPrimaryKey(FansDO row);

    int deleteByUserIdAndFansUserId(@Param("userId") Long userId,
                                    @Param("fansUserId") Long fansUserId);

    // 查询记录总数
    long selectCountByUserId(Long userId);

    // 分页查询
    List<FansDO> selectPageListByUserId(@Param("userId") Long userId,
                                        @Param("offset") long offset,
                                        @Param("limit") long limit);

    // 查询最新关注的 5000 位粉丝
    List<FansDO> select5000FansByUserId(Long userId);
}