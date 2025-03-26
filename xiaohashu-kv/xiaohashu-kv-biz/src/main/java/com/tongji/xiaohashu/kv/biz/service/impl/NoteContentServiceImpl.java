package com.tongji.xiaohashu.kv.biz.service.impl;

import com.tongji.framework.common.exception.BizException;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.kv.biz.domain.dataobject.NoteContentDO;
import com.tongji.xiaohashu.kv.biz.domain.repository.NoteContentRepository;
import com.tongji.xiaohashu.kv.biz.enums.ResponseCodeEnum;
import com.tongji.xiaohashu.kv.biz.service.NoteContentService;
import com.tongji.xiaohashu.kv.dto.req.AddNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.DeleteNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.FindNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author tongji
 * @time 2025/3/21 10:41
 * @description 笔记内容
 */
@Service
@Slf4j
public class NoteContentServiceImpl implements NoteContentService {
    @Resource
    private NoteContentRepository noteContentRepository;

    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        // 笔记 ID
        String uuid = addNoteContentReqDTO.getUuid();
        // 笔记内容
        String content = addNoteContentReqDTO.getContent();

        // 构建数据库 DO 实体类
        NoteContentDO nodeContentDO = NoteContentDO.builder()
                .id(UUID.fromString(uuid))
                .content(content)
                .build();

        noteContentRepository.save(nodeContentDO);

        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        String uuid = findNoteContentReqDTO.getUuid();
        // 根据 id 查询内容
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(uuid));

        if (optional.isEmpty()) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }

        NoteContentDO noteContentDO = optional.get();
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .uuid(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();

        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        String uuid = deleteNoteContentReqDTO.getUuid();
        // 删除笔记内容
        noteContentRepository.deleteById(UUID.fromString(uuid));

        return Response.success();
    }
}
