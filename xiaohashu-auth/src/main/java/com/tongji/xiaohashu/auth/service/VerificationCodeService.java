package com.tongji.xiaohashu.auth.service;

import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;

/**
 * @author tongji
 * @time 2025/3/14 11:19
 * @description
 */
public interface VerificationCodeService {
    /**
     * 发送短信验证码
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
