package com.tongji.xiaohashu.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tongji
 * @time 2025/3/26 11:25
 * @description
 */
@SpringBootApplication
@MapperScan("com.tongji.xiaohashu.user.relation.biz.domain.mapper")
public class XiaohashuUserRelationBizApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaohashuUserRelationBizApplication.class, args);
    }
}
