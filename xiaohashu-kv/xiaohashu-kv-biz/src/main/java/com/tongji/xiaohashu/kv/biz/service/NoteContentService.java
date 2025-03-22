package com.tongji.xiaohashu.kv.biz.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.kv.dto.req.AddNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.DeleteNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.FindNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.rsp.FindNoteContentRspDTO;

/**
 * @author tongji
 * @time 2025/3/21 10:41
 * @description 笔记内容存储业务
 */
public interface NoteContentService {
    // 添加笔记内容
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    // 查询笔记内容
    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    // 删除笔记内容
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
