package com.tongji.xiaohashu.auth.controller;

import com.tongji.framework.biz.operationlog.aspect.ApiOperationLog;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.model.vo.user.UpdatePasswordReqVO;
import com.tongji.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import com.tongji.xiaohashu.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author tongji
 * @time 2025/3/15 11:22
 * @description
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO userLoginReqVO) {
        return userService.loginAndRegister(userLoginReqVO);
    }

    @PostMapping("/logout")
    @ApiOperationLog(description = "账号登出")
    public Response<?> logout() {
        return userService.logout();
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "修改密码")
    public Response<?> updatePassword(@Validated @RequestBody UpdatePasswordReqVO updatePasswordReqVO) {
        return userService.updatePassword(updatePasswordReqVO);
    }
}
