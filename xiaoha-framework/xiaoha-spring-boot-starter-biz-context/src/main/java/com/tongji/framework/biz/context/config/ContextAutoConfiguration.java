package com.tongji.framework.biz.context.config;

import com.tongji.framework.biz.context.filter.HeaderUserId2ContextFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author tongji
 * @time 2025/3/16 19:40
 * @description
 */
@AutoConfiguration
public class ContextAutoConfiguration {
    @Bean
    public FilterRegistrationBean<HeaderUserId2ContextFilter> filterRegistrationBean() {
        HeaderUserId2ContextFilter filter = new HeaderUserId2ContextFilter();

        return new FilterRegistrationBean<>(filter);
    }
}
