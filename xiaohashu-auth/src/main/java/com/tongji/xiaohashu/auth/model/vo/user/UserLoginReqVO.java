package com.tongji.xiaohashu.auth.model.vo.user;

import com.tongji.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/15 10:49
 * @description 用户登录 入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginReqVO {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;

    /**
     * 验证码
     */
    private String code;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型：手机号验证码，或者是账号密码
     * 1表示手机号验证码登录；2表示账号密码登录
     */
    @NotNull(message = "登录类型不能为空")
    private Integer type;
}
