package com.tongji.framework.biz.context.interceptor;

import com.tongji.framework.biz.context.holder.LoginUserContextHolder;
import com.tongji.framework.common.constant.GlobalConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/19 14:54
 * @description Feign 请求拦截器
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前上下文中用户的 ID
        Long userId = LoginUserContextHolder.getUserId();

        if (Objects.nonNull(userId)) {
            requestTemplate.header(GlobalConstants.USER_ID, String.valueOf(userId));
            log.info("########## feign 请求设置请求头 userId: {}", userId);
        }
    }
}
