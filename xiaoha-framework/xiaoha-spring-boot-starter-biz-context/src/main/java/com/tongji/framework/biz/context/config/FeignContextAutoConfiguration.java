package com.tongji.framework.biz.context.config;

import com.tongji.framework.biz.context.interceptor.FeignRequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author tongji
 * @time 2025/3/19 14:56
 * @description Feign 请求拦截器自动配置
 */
@AutoConfiguration
public class FeignContextAutoConfiguration {
    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
