package com.tongji.xiaohashu.oss.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tongji
 * @time 2025/3/19 14:31
 * @description 处理表单提交
 */
@Configuration
public class FeignFormConfig {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
