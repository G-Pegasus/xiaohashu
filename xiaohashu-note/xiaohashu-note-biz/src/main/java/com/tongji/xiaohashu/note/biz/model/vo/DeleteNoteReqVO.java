package com.tongji.xiaohashu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tongji
 * @time 2025/3/26 10:28
 * @description 删除笔记入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteNoteReqVO {
    @NotNull(message = "笔记 ID 不能为空")
    private Long id;
}
