package com.tongji.xiaohashu.count.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/28 13:07
 * @description 关注、取关 Type
 */
@Getter
@AllArgsConstructor
public enum FollowUnfollowTypeEnum {
    // 关注
    FOLLOW(1),
    // 取关
    UNFOLLOW(0),
    ;

    private final Integer code;

    public static FollowUnfollowTypeEnum valueOf(Integer code) {
        for (FollowUnfollowTypeEnum e : FollowUnfollowTypeEnum.values()) {
            if (Objects.equals(e.code, code)) {
                return e;
            }
        }
        return null;
    }
}
