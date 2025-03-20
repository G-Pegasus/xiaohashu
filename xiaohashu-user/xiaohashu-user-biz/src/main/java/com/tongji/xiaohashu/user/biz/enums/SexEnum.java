package com.tongji.xiaohashu.user.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/19 11:25
 * @description
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
    WOMAN(0),
    MAN(1);

    private final Integer value;

    public static boolean isValid(Integer value) {
        for (SexEnum loginTypeEnum : SexEnum.values()) {
            if (Objects.equals(value, loginTypeEnum.getValue())) {
                return true;
            }
        }

        return false;
    }
}
