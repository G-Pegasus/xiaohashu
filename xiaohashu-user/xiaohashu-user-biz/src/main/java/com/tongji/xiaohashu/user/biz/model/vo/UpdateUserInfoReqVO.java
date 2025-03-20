package com.tongji.xiaohashu.user.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * @author tongji
 * @time 2025/3/19 11:25
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInfoReqVO {
    // 头像
    private MultipartFile avatar;
    // 昵称
    private String nickname;
    // 小哈书 ID
    private String xiaohashuId;
    // 性别
    private Integer sex;
    // 生日
    private LocalDate birthday;
    // 个人介绍
    private String introduction;
    // 背景图
    private MultipartFile backgroundImg;
}
