package com.tongji.xiaohashu.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/15 10:51
 * @description 登录类型枚举类
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    // 验证码登录
    VERIFICATION_CODE(1),
    // 密码登录
    PASSWORD(2);

    private final int value;

    public static LoginTypeEnum valueOf(Integer code) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (Objects.equals(loginTypeEnum.getValue(), code)) {
                return loginTypeEnum;
            }
        }

        return null;
    }
}
