package com.tongji.xiaohashu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tongji
 * @time 2025/3/24 14:26
 * @description 笔记状态
 */
@Getter
@AllArgsConstructor
public enum NoteStatusEnum {
    BE_EXAMINE(0), // 待审核
    NORMAL(1), // 正常展示
    DELETED(2), // 被删除
    DOWNED(3), // 被下架
    ;

    private final Integer code;
}
