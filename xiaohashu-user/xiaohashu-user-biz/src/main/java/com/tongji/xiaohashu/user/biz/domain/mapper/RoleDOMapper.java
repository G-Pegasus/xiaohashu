package com.tongji.xiaohashu.user.biz.domain.mapper;

import com.tongji.xiaohashu.user.biz.domain.dataobject.RoleDO;

import java.util.List;

public interface RoleDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleDO row);

    int insertSelective(RoleDO row);

    RoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleDO row);

    int updateByPrimaryKey(RoleDO row);

    List<RoleDO> selectEnabledList();
}