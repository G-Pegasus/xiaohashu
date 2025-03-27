package com.tongji.xiaohashu.user.relation.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/27 19:49
 * @description 查询关注列表出参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindFollowingUserRspVO {
    private Long userId;

    private String avatar;

    private String nickname;

    private String introduction;
}
