package com.tongji.xiaohashu.oss.biz.strategy.impl;

import com.tongji.xiaohashu.oss.biz.config.MinioProperties;
import com.tongji.xiaohashu.oss.biz.strategy.FileStrategy;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author tongji
 * @time 2025/3/18 16:32
 * @description
 */
@Slf4j
public class MinioFileStrategy implements FileStrategy {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    @Override
    @SneakyThrows
    public String uploadFile(MultipartFile file, String bucketName) {
        log.info("## 上传文件至 Minio ...");

        if (file == null || file.getSize() == 0) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }

        // 文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 文件的 ContentType
        String contentType = file.getContentType();

        // 生成存储对象的名称（将 UUID 字符串中的 - 替换成空字符）
        String key = UUID.randomUUID().toString().replace("-", "");
        // 获取文件的后缀，如 .jpg
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 拼接上文件后缀
        String objectName = String.format("%s%s", key, suffix);

        log.info("==> 开始上传文件至 Minio, ObjectName: {}", objectName);

        // 上传文件至 Minio
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build());

        // 返回文件的访问链接
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), bucketName, objectName);
        log.info("==> 上传文件至 Minio 成功，访问路径: {}", url);

        return url;
    }
}
