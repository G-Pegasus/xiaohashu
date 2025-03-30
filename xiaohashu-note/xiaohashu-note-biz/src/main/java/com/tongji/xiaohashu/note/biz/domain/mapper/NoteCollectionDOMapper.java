package com.tongji.xiaohashu.note.biz.domain.mapper;

import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteCollectionDO;

public interface NoteCollectionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteCollectionDO row);

    int insertSelective(NoteCollectionDO row);

    NoteCollectionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteCollectionDO row);

    int updateByPrimaryKey(NoteCollectionDO row);
}