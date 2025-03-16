package com.tongji.xiaohashu.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.gateway.enums.ResponseCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tongji
 * @time 2025/3/16 16:18
 * @description 网关层全局异常捕获
 */
@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Resource
    private ObjectMapper objectMapper;

    @NotNull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable ex) {
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();

        log.error("==> 全局异常捕获", ex);

        // 响应
        Response<?> result;
        // 根据捕获的异常类型，设置不同的响应状态码和响应消息
        if (ex instanceof SaTokenException) {
            // 权限认证失败时，设置401状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 构建响应结果
            result = Response.fail(ResponseCodeEnum.UNAUTHORIZED.getErrorCode(), ex.getMessage());
        } else {
            result = Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
        }

        // 设置响应头的内容类型为 application/json;charset=UTF-8，表示响应体为 JSON 格式
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 设置 body 响应体
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                // 使用 ObjectMapper 将 result 对象转换为 JSON 字节数组
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            } catch (Exception e) {
                // 如果转换过程中出现异常，则返回空字节数组
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
