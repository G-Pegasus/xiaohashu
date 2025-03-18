package com.tongji.xiaohashu.oss.biz.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author tongji
 * @time 2025/3/18 16:31
 * @description 文件策略接口
 */
public interface FileStrategy {
    // 文件上传
    String uploadFile(MultipartFile file, String bucketName);
}
