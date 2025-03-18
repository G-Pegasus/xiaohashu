package com.tongji.xiaohashu.oss.biz.factory;

import com.tongji.xiaohashu.oss.biz.strategy.FileStrategy;
import com.tongji.xiaohashu.oss.biz.strategy.impl.AliyunOSSFileStrategy;
import com.tongji.xiaohashu.oss.biz.strategy.impl.MinioFileStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tongji
 * @time 2025/3/18 16:34
 * @description 按需初始化具体的策略实现类
 */
@RefreshScope
@Configuration
public class FileStrategyFactory {
    @Value("${storage.type}")
    private String strategyType;

    @Bean
    @RefreshScope
    public FileStrategy getFileStrategy() {
        return switch (strategyType) {
            case "aliyun" -> new AliyunOSSFileStrategy();
            case "minio" -> new MinioFileStrategy();
            default -> throw new IllegalArgumentException("不可用的存储类型");
        };
    }
}
