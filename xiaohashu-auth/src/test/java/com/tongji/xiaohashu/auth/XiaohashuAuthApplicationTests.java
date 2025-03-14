package com.tongji.xiaohashu.auth;

import com.tongji.xiaohashu.auth.domain.dataobject.UserDO;
import com.tongji.xiaohashu.auth.domain.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class XiaohashuAuthApplicationTests {

    @Resource
    private UserDOMapper userMapper;

    @Test
    void testInsert() {
        UserDO user = UserDO.builder()
                .username("tongji")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        userMapper.insert(user);
    }

    @Test
    void testSelect() {
        UserDO userDO = userMapper.selectByPrimaryKey(1L);
        log.info("User: {}", userDO.toString());
    }

    @Test
    void testUpdate() {
        UserDO userDO = UserDO.builder()
                .id(1L)
                .username("wenjuan")
                .updateTime(LocalDateTime.now())
                .build();

        // 根据主键 ID 更新记录
        userMapper.updateByPrimaryKey(userDO);
    }

    @Test
    void testDelete() {
        // 删除主键 ID 为 4 的记录
        userMapper.deleteByPrimaryKey(1L);
    }
}
