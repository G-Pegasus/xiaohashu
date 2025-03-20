package com.tongji.xiaohashu.user.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/20 20:04
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserByPhoneRspDTO {
    private Long id;
    private String password;
}
