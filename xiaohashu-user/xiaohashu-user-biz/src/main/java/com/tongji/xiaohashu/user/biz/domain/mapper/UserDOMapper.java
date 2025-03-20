package com.tongji.xiaohashu.user.biz.domain.mapper;

import com.tongji.xiaohashu.user.biz.domain.dataobject.UserDO;

public interface UserDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO row);

    int insertSelective(UserDO row);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO row);

    int updateByPrimaryKey(UserDO row);

    UserDO selectByPhone(String phone);
}