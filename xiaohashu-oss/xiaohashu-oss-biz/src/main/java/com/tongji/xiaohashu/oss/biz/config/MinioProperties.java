package com.tongji.xiaohashu.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tongji
 * @time 2025/3/18 17:11
 * @description
 */
@ConfigurationProperties(prefix = "storage.minio")
@Component
@Data
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
