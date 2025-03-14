package com.tongji.xiaohashu.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author tongji
 * @time 2025/3/13 14:50
 * @description 测试新日期切面
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String nickname;
    private LocalDateTime createTime;
}
