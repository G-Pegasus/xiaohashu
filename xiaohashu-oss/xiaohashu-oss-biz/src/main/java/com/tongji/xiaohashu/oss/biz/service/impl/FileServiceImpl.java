package com.tongji.xiaohashu.oss.biz.service.impl;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.oss.biz.service.FileService;
import com.tongji.xiaohashu.oss.biz.strategy.FileStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tongji
 * @time 2025/3/18 16:38
 * @description
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Resource
    private FileStrategy fileStrategy;

    private static final String BUCKET_NAME = "xiaohashu";

    @Override
    public Response<?> uploadFile(MultipartFile file) {
        // 文件上传
        String url = fileStrategy.uploadFile(file, BUCKET_NAME);

        return Response.success(url);
    }
}
