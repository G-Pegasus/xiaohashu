package com.tongji.xiaohashu.gateway.auth;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.dev33.satoken.SaManager.log;

/**
 * @author tongji
 * @time 2025/3/16 11:02
 * @description [Sa-Token 权限认证] 配置类
 */
@Configuration
public class SaTokenConfigure {
    // 注册 Sa-Token 全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 鉴权方法，每次访问进入
                .setAuth(obj -> {
                    log.info("==================> SaReactorFilter, Path: {}", SaHolder.getRequest().getRequestPath());
                   // 登录校验 -- 拦截所有路由
                    SaRouter.match("/**")
                            .notMatch("/auth/user/login") // 排除登录接口
                            .notMatch("/auth/verification/code/send") // 排除验证码发送接口
                            .check(r -> StpUtil.checkLogin());

                    // 权限认证 -- 不同模块，校验不同权限
                    SaRouter.match("/auth/user/logout", r -> StpUtil.checkPermission("app:note:publish"));
                    // SaRouter.match("/auth/user/logout", r -> StpUtil.checkRole("admin"));
                    // SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                    // SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
                    // ...
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    // 手动抛出异常，抛给全局异常处理器
                    if (e instanceof NotLoginException) { // 未登录异常
                        throw new NotLoginException(e.getMessage(), null, null);
                    } else if (e instanceof NotPermissionException || e instanceof NotRoleException) { // 权限不足，或不具备角色，统一抛出权限不足异常
                        throw new NotPermissionException(e.getMessage());
                    } else { // 其他异常，则抛出一个运行时异常
                        throw new RuntimeException(e.getMessage());
                    }
                });
    }
}
