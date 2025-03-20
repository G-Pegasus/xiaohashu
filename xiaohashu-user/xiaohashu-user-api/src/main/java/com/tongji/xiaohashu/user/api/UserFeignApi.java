package com.tongji.xiaohashu.user.api;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.constant.ApiConstants;
import com.tongji.xiaohashu.user.dto.req.FindUserByPhoneReqDTO;
import com.tongji.xiaohashu.user.dto.req.RegisterUserReqDTO;
import com.tongji.xiaohashu.user.dto.req.UpdateUserPasswordReqDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByPhoneRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tongji
 * @time 2025/3/20 13:56
 * @description
 */
@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface UserFeignApi {
    String PREFIX = "/user";

    @PostMapping(value = PREFIX + "/register")
    Response<Long> registerUser(@RequestBody RegisterUserReqDTO registerUserReqDTO);

    @PostMapping(value = PREFIX + "/findByPhone")
    Response<FindUserByPhoneRspDTO> findByPhone(@RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    @PostMapping(value = PREFIX + "/password/update")
    Response<?> updatePassword(@RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO);
}
