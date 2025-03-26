package com.tongji.xiaohashu.note.biz.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.note.biz.model.vo.*;

/**
 * @author tongji
 * @time 2025/3/24 16:17
 * @description 笔记业务
 */
public interface NoteService {
    // 笔记发布
    Response<?> publishNote(PublishNoteReqVO publishNoteReqVO);

    // 笔记详情
    Response<FindNoteDetailRspVO> findNoteDetail(FindNoteDetailReqVO findNoteDetailReqVO);

    // 笔记更新
    Response<?> updateNote(UpdateNoteReqVO updateNoteReqVO);

    // 删除本地缓存
    void deleteNoteLocalCache(Long noteId);

    // 删除笔记
    Response<?> deleteNote(DeleteNoteReqVO deleteNoteReqVO);

    // 笔记仅为自己可见
    Response<?> visibleOnlyMe(UpdateNoteVisibleOnlyMeReqVO updateNoteVisibleOnlyMeReqVO);

    // 笔记置顶 / 取消置顶
    Response<?> topNote(TopNoteReqVO topNoteReqVO);
}
