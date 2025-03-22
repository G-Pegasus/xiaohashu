package com.tongji.xiaohashu.kv.biz;

import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.kv.biz.domain.dataobject.NoteContentDO;
import com.tongji.xiaohashu.kv.biz.domain.repository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

/**
 * @author tongji
 * @time 2025/3/21 10:15
 * @description
 */
@SpringBootTest
@Slf4j
public class CassandraTests {
    @Resource
    private NoteContentRepository noteContentRepository;

    @Test
    void testInsert() {
        NoteContentDO noteContentDO = NoteContentDO.builder()
                .id(UUID.randomUUID())
                .content("代码测试笔记内容插入")
                .build();

        noteContentRepository.save(noteContentDO);
    }

    @Test
    void testUpdate() {
        NoteContentDO noteContentDO = NoteContentDO.builder()
                .id(UUID.fromString("a51ed554-ea3b-44a7-ad5c-5e67a9f8e52c"))
                .content("代码测试笔记内容更新")
                .build();

        noteContentRepository.save(noteContentDO);
    }

    @Test
    void testSelect() {
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString("a51ed554-ea3b-44a7-ad5c-5e67a9f8e52c"));
        optional.ifPresent(noteContentDO -> log.info("查询结果：{}", JsonUtils.toJsonString(noteContentDO)));
    }
}
