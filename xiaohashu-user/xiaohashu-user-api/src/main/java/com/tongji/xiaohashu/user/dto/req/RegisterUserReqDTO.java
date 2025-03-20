package com.tongji.xiaohashu.user.dto.req;

import com.tongji.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/20 13:28
 * @description 用户注册
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserReqDTO {
    // 手机号
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;
}
