package com.tongji.xiaohashu.kv.api;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.kv.constant.ApiConstants;
import com.tongji.xiaohashu.kv.dto.req.AddNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.DeleteNoteContentReqDTO;
import com.tongji.xiaohashu.kv.dto.req.FindNoteContentReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author tongji
 * @time 2025/3/21 10:56
 * @description
 */
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface KeyValueFeignApi {
    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/find")
    Response<?> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);
}
