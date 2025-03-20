package com.tongji.xiaohashu.auth.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.model.vo.user.UpdatePasswordReqVO;
import com.tongji.xiaohashu.auth.model.vo.user.UserLoginReqVO;

/**
 * @author tongji
 * @time 2025/3/15 11:03
 * @description
 */
public interface AuthService {
    // 注册与登录
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

    // 退出登录
    Response<?> logout();

    // 修改密码
    Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO);
}
