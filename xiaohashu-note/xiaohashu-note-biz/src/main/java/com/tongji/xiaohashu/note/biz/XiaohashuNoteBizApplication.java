package com.tongji.xiaohashu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author tongji
 * @time 2025/3/24 14:09
 * @description
 */
@SpringBootApplication
@MapperScan("com.tongji.xiaohashu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.tongji.xiaohashu")
public class XiaohashuNoteBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohashuNoteBizApplication.class, args);
    }
}
