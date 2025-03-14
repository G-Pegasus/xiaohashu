package com.tongji.framework.common.response;

import com.tongji.framework.common.exception.BaseExceptionInterface;
import com.tongji.framework.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    // 是否成功
    private boolean success = true;
    // 响应消息
    private T data;
    private String errorCode;
    private String errorMessage;

    public static <T> Response<T> success() {
        return new Response<T>();
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<T>();
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail() {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String errorMessage) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(String errorCode, String errorMessage) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        response.setErrorMessage(baseExceptionInterface.getErrorMessage());
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setErrorCode(bizException.getErrorCode());
        response.setErrorMessage(bizException.getErrorMessage());
        return response;
    }
}
