package com.tongji.xiaohashu.note.biz.domain.mapper;

import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteDO;

public interface NoteDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteDO row);

    int insertSelective(NoteDO row);

    NoteDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteDO row);

    int updateByPrimaryKey(NoteDO row);

    int updateVisibleOnlyMe(NoteDO noteDO);

    int updateIsTop(NoteDO noteDO);
}