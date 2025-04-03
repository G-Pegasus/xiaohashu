package com.tongji.xiaohashu.note.biz.domain.mapper;

import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteCollectionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteCollectionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteCollectionDO row);

    int insertSelective(NoteCollectionDO row);

    NoteCollectionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteCollectionDO row);

    int updateByPrimaryKey(NoteCollectionDO row);

    int selectCountByUserIdAndNoteId(@Param("userId") Long userId, @Param("noteId") Long noteId);

    List<NoteCollectionDO> selectByUserId(Long userId);
    // 查询笔记是否已经被收藏
    int selectNoteIsCollected(@Param("userId") Long userId, @Param("noteId") Long noteId);
    // 查询用户最近收藏的笔记
    List<NoteCollectionDO> selectCollectedByUserIdAndLimit(@Param("userId") Long userId, @Param("limit")  int limit);

    int insertOrUpdate(NoteCollectionDO noteCollectionDO);

    int update2UnCollectByUserIdAndNoteId(NoteCollectionDO noteCollectionDO);
}