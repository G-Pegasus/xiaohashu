package com.tongji.xiaohashu.note.biz.domain.mapper;

import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteLikeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteLikeDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteLikeDO row);

    int insertSelective(NoteLikeDO row);

    NoteLikeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteLikeDO row);

    int updateByPrimaryKey(NoteLikeDO row);

    int selectCountByUserIdAndNoteId(@Param("userId") Long userId, @Param("noteId") Long noteId);

    List<NoteLikeDO> selectByUserId(@Param("userId") Long userId);

    int selectNoteIsLiked(@Param("userId") Long userId, @Param("noteId") Long noteId);

    List<NoteLikeDO> selectLikedByUserIdAndLimit(@Param("userId") Long userId, @Param("limit")  int limit);

    /**
     * 新增笔记点赞记录，若已存在，则更新笔记点赞记录
     */
    int insertOrUpdate(NoteLikeDO noteLikeDO);

    // 取消点赞
    int update2UnlikeByUserIdAndNoteId(NoteLikeDO noteLikeDO);
}