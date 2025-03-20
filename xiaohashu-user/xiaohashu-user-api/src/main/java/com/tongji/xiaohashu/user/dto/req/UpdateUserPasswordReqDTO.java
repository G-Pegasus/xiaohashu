package com.tongji.xiaohashu.user.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/20 20:25
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserPasswordReqDTO {
    @NotBlank(message = "密码不能为空")
    private String encodePassword;
}
