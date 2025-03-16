package com.tongji.xiaohashu.gateway.enums;

import com.tongji.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/16 16:15
 * @description
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // 通用异常状态码
    SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),
    UNAUTHORIZED("401", "权限不足"),

    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}
