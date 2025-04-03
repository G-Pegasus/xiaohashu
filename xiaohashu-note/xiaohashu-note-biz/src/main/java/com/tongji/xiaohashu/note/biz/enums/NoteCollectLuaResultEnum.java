package com.tongji.xiaohashu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/4/2 10:22
 * @description
 */
@Getter
@AllArgsConstructor
public enum NoteCollectLuaResultEnum {
    // 布隆过滤器或者 ZSet 不存在
    NOT_EXIST(-1L),
    // 笔记已收藏
    NOTE_COLLECTED(1L),
    // 笔记收藏成功
    NOTE_COLLECTED_SUCCESS(0L),
    ;

    private final Long code;

    /**
     * 根据类型 code 获取对应的枚举
     */
    public static NoteCollectLuaResultEnum valueOf(Long code) {
        for (NoteCollectLuaResultEnum noteCollectLuaResultEnum : NoteCollectLuaResultEnum.values()) {
            if (Objects.equals(code, noteCollectLuaResultEnum.getCode())) {
                return noteCollectLuaResultEnum;
            }
        }
        return null;
    }
}
