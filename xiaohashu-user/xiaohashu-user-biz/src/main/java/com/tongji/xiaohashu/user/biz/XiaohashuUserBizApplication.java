package com.tongji.xiaohashu.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author tongji
 * @time 2025/3/19 10:48
 * @description
 */
@SpringBootApplication
@MapperScan("com.tongji.xiaohashu.user.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.tongji.xiaohashu")
public class XiaohashuUserBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohashuUserBizApplication.class, args);
    }
}
