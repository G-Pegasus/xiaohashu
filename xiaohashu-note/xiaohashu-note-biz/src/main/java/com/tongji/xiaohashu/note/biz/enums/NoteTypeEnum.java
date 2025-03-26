package com.tongji.xiaohashu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/24 14:27
 * @description 笔记类型
 */
@Getter
@AllArgsConstructor
public enum NoteTypeEnum {
    IMAGE_TEXT(0, "图文"),
    VIDEO(1, "视频");

    private final Integer code;
    private final String description;

    public static boolean isValid(Integer code) {
        for (NoteTypeEnum noteTypeEnum : NoteTypeEnum.values()) {
            if (noteTypeEnum.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }

    public static NoteTypeEnum valueOf(Integer code) {
        for (NoteTypeEnum noteTypeEnum : NoteTypeEnum.values()) {
            if (noteTypeEnum.getCode().equals(code)) {
                return noteTypeEnum;
            }
        }

        return null;
    }
}
