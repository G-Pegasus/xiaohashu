package com.tongji.xiaohashu.user.biz.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.tongji.xiaohashu.user.dto.req.FindUserByIdReqDTO;
import com.tongji.xiaohashu.user.dto.req.FindUserByPhoneReqDTO;
import com.tongji.xiaohashu.user.dto.req.RegisterUserReqDTO;
import com.tongji.xiaohashu.user.dto.req.UpdateUserPasswordReqDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByIdRspDTO;
import com.tongji.xiaohashu.user.dto.resp.FindUserByPhoneRspDTO;

/**
 * @author tongji
 * @time 2025/3/19 11:25
 * @description 用户业务
 */
public interface UserService {
    // 更新用户信息
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    // 用户注册
    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);

    // 根据手机号查询用户信息
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    // 更新密码
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    // 根据用户 ID 查询用户信息
    Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO);
}
