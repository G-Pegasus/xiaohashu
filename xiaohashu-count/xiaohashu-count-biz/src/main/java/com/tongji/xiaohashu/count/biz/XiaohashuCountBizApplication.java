package com.tongji.xiaohashu.count.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tongji
 * @time 2025/3/28 10:57
 * @description
 */
@SpringBootApplication
@MapperScan("com.tongji.xiaohashu.count.biz.domain.mapper")
public class XiaohashuCountBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohashuCountBizApplication.class, args);
    }
}
