package com.tongji.xiaohashu.note.biz.rpc;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.kv.api.KeyValueFeignApi;
import com.tongji.xiaohashu.kv.dto.req.AddNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.DeleteNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.FindNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.rsp.FindNoteContentRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/24 15:58
 * @description KV 键值服务
 */
@Component
public class KeyValueRpcService {
    @Resource private KeyValueFeignApi keyValueFeignApi;

    // 保存笔记内容
    public boolean saveNoteContent(String uuid, String content) {
        AddNoteContentReqDTO addNoteContentReqDTO = new AddNoteContentReqDTO();
        addNoteContentReqDTO.setUuid(uuid);
        addNoteContentReqDTO.setContent(content);

        Response<?> response = keyValueFeignApi.addNoteContent(addNoteContentReqDTO);

        return !Objects.isNull(response) && response.isSuccess();
    }

    // 删除笔记内容
    public boolean deleteNoteContent(String uuid) {
        DeleteNoteContentReqDTO deleteNoteContentReqDTO = new DeleteNoteContentReqDTO();
        deleteNoteContentReqDTO.setUuid(uuid);

        Response<?> response = keyValueFeignApi.deleteNoteContent(deleteNoteContentReqDTO);

        return !Objects.isNull(response) && response.isSuccess();
    }

    // 查询笔记内容
    public String findNoteContent(String uuid) {
        FindNoteContentReqDTO findNoteContentReqDTO = new FindNoteContentReqDTO();
        findNoteContentReqDTO.setUuid(uuid);

        Response<FindNoteContentRspDTO> response = keyValueFeignApi.findNoteContent(findNoteContentReqDTO);

        if (Objects.isNull(response) || !response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }

        return response.getData().getContent();
    }
}
