package com.tongji.xiaohashu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/30 21:30
 * @description 笔记点赞、取消点赞 Type
 */
@Getter
@AllArgsConstructor
public enum LikeUnLikeNoteTypeEnum {
    // 点赞
    LIKE(1),
    // 取消点赞
    UNLIKE(0),
    ;

    private final Integer code;
}
