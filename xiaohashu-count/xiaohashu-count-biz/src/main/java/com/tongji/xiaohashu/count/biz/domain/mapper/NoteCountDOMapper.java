package com.tongji.xiaohashu.count.biz.domain.mapper;

import com.tongji.xiaohashu.count.biz.domain.dataobject.NoteCountDO;

public interface NoteCountDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteCountDO row);

    int insertSelective(NoteCountDO row);

    NoteCountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteCountDO row);

    int updateByPrimaryKey(NoteCountDO row);
}