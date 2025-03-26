package com.tongji.xiaohashu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/24 14:27
 * @description 笔记是否可见
 */
@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {
    PUBLIC(0), // 公开，所有人可见
    PRIVATE(1); // 仅自己可见

    private final Integer code;
}
