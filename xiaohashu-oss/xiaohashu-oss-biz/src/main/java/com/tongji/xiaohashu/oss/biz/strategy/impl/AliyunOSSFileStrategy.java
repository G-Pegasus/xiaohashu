package com.tongji.xiaohashu.oss.biz.strategy.impl;

import com.aliyun.oss.OSS;
import com.tongji.xiaohashu.oss.biz.config.AliyunOSSProperties;
import com.tongji.xiaohashu.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * @author tongji
 * @time 2025/3/18 16:33
 * @description
 */
@Slf4j
public class AliyunOSSFileStrategy implements FileStrategy {
    @Resource
    private AliyunOSSProperties aliyunOSSProperties;
    @Resource
    private OSS ossClient;

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {
        log.info("## 上传文件至阿里云 OSS ...");

        // 判断文件是否为空
        if (file == null || file.getSize() == 0) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }

        // 文件的原始名称
        String originalFilename = file.getOriginalFilename();

        // 生成存储对象的名称
        String key = UUID.randomUUID().toString().replace("-", "");
        // 后缀
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 拼接上文件后缀，即为要存储的文件名
        String objectName = String.format("%s%s", key, suffix);

        log.info("==> 开始上传文件至阿里云 OSS, ObjectName: {}", objectName);

        // 上传文件至阿里云 OSS
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getInputStream().readAllBytes()));
        // 返回文件的访问链接
        String url = String.format("https://%s.%s/%s", bucketName, aliyunOSSProperties.getEndpoint(), objectName);

        log.info("==> 上传文件至阿里云 OSS 成功，访问路径: {}", url);
        return url;
    }
}
