package com.tongji.xiaohashu.note.biz.rpc;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.api.UserFeignApi;
import com.tongji.xiaohashu.user.dto.req.FindUserByIdReqDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/25 16:13
 * @description 用户服务
 */
@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;

    // 查询用户信息
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }
}
