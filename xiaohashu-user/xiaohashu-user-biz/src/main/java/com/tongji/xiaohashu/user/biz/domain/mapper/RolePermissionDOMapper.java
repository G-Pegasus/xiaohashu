package com.tongji.xiaohashu.user.biz.domain.mapper;

import com.tongji.xiaohashu.user.biz.domain.dataobject.RolePermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionDO row);

    int insertSelective(RolePermissionDO row);

    RolePermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermissionDO row);

    int updateByPrimaryKey(RolePermissionDO row);

    List<RolePermissionDO> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}