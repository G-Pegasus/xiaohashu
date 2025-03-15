package com.tongji.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/15 11:24
 * @description 逻辑删除
 */
@Getter
@AllArgsConstructor
public enum DeletedEnum {
    YES(true),
    NO(false);

    private final Boolean value;
}
