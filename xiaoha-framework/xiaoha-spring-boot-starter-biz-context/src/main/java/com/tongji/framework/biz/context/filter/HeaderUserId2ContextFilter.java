package com.tongji.framework.biz.context.filter;

import com.tongji.framework.biz.context.holder.LoginUserContextHolder;
import com.tongji.framework.common.constant.GlobalConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author tongji
 * @time 2025/3/16 19:43
 * @description 提取请求头中的用户 ID 保存到上下文中，以方便后续使用
 */
@Slf4j
public class HeaderUserId2ContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取用户ID
        String userId = request.getHeader(GlobalConstants.USER_ID);

        log.info("## HeaderUserId2ContextFilter, 用户 ID: {}", userId);

        // 判断请求头中是否存在用户 ID
        if (StringUtils.isBlank(userId)) {
            chain.doFilter(request, response);
            return;
        }

        // 如果 header 中存在 userId, 则设置到 ThreadLocal
        log.info("===== 设置 userId 到 ThreadLocal 中， 用户 ID: {}", userId);
        LoginUserContextHolder.setUserId(userId);

        try {
            chain.doFilter(request, response);
        } finally {
            // 一定要删除 ThreadLocal ，防止内存泄露
            LoginUserContextHolder.remove();
            log.info("===== 删除 ThreadLocal， userId: {}", userId);
        }
    }
}
