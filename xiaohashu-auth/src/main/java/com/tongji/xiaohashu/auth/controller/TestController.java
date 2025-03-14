package com.tongji.xiaohashu.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tongji.framework.biz.operationlog.aspect.ApiOperationLog;
import com.tongji.framework.common.response.Response;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response<String> test() {
        return Response.success("Hello Tongji");
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2(@RequestBody User user) {
        return Response.success(user);
    }

    @RequestMapping("/user/doLogin")
    public String doLogin(String username, String password) {
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/user/isLogin")
    public String isLogin() {
        return "当前回话是否登录：" + StpUtil.isLogin();
    }
}
