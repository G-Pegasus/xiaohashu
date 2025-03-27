package com.tongji.xiaohashu.user.relation.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.api.UserFeignApi;
import com.tongji.xiaohashu.user.dto.req.FindUserByIdReqDTO;
import com.tongji.xiaohashu.user.dto.req.FindUserByIdsReqDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/26 20:53
 * @description 用户服务
 */
@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;

    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }

        return response.getData();
    }

    public List<FindUserByIdRspDTO> findByIds(List<Long> userIds) {
        FindUserByIdsReqDTO findUserByIdsReqDTO = new FindUserByIdsReqDTO();
        findUserByIdsReqDTO.setIds(userIds);

        Response<List<FindUserByIdRspDTO>> response = userFeignApi.findByIds(findUserByIdsReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }
}
