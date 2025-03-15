package com.tongji.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/15 11:25
 * @description 状态
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    // 启用
    ENABLE(0),
    DISABLE(1);

    private final Integer value;
}
