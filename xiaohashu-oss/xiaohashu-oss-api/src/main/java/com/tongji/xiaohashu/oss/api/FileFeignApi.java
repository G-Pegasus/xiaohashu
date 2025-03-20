package com.tongji.xiaohashu.oss.api;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.oss.config.FeignFormConfig;
import com.tongji.xiaohashu.oss.constant.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tongji
 * @time 2025/3/19 14:20
 * @description
 */
@FeignClient(name = ApiConstants.SERVICE_NAME, configuration = FeignFormConfig.class)
public interface FileFeignApi {
    String PREFIX = "/file";

    // 文件上传
    @PostMapping(value = PREFIX + "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<?> uploadFile(@RequestPart(value = "file") MultipartFile file);
}
