package com.tongji.xiaohashu.auth.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.model.vo.user.UserLoginReqVO;

/**
 * @author tongji
 * @time 2025/3/15 11:03
 * @description
 */
public interface UserService {
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);
}
