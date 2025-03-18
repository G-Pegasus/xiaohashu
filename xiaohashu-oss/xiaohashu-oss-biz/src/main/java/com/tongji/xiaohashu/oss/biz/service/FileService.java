package com.tongji.xiaohashu.oss.biz.service;

import com.tongji.framework.common.response.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tongji
 * @time 2025/3/18 16:37
 * @description
 */
public interface FileService {
    Response<?> uploadFile(MultipartFile file);
}
