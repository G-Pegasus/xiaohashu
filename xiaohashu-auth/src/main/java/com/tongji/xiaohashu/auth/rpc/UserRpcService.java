package com.tongji.xiaohashu.auth.rpc;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.api.UserFeignApi;
import com.tongji.xiaohashu.user.dto.req.FindUserByPhoneReqDTO;
import com.tongji.xiaohashu.user.dto.req.RegisterUserReqDTO;
import com.tongji.xiaohashu.user.dto.req.UpdateUserPasswordReqDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author tongji
 * @time 2025/3/20 15:55
 * @description 用户服务
 */
@Component
public class UserRpcService {
    @Resource
    private UserFeignApi userFeignApi;

    // 用户注册
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);

        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    // 根据手机号查询用户信息
    public FindUserByPhoneRspDTO findUserByPhone(String phone) {
        FindUserByPhoneReqDTO findUserByPhoneReqDTO = new FindUserByPhoneReqDTO();
        findUserByPhoneReqDTO.setPhone(phone);

        Response<FindUserByPhoneRspDTO> response = userFeignApi.findByPhone(findUserByPhoneReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    // 密码更新
    public void updatePassword(String encodePassword) {
        UpdateUserPasswordReqDTO updateUserPasswordReqDTO = new UpdateUserPasswordReqDTO();
        updateUserPasswordReqDTO.setEncodePassword(encodePassword);

        userFeignApi.updatePassword(updateUserPasswordReqDTO);
    }
}
