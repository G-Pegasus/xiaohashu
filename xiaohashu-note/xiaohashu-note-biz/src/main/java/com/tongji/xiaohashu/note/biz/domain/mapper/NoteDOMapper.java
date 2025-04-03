package com.tongji.xiaohashu.note.biz.domain.mapper;

import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteDO;
import org.apache.ibatis.annotations.Param;

public interface NoteDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteDO row);

    int insertSelective(NoteDO row);

    NoteDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteDO row);

    int updateByPrimaryKey(NoteDO row);

    int updateVisibleOnlyMe(NoteDO noteDO);

    int updateIsTop(NoteDO noteDO);

    int selectCountByNoteId(Long noteId);

    // 查询笔记的发布者用户 ID
    Long selectCreatorIdByNoteId(Long noteId);
}