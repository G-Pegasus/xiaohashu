package com.tongji.xiaohashu.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author tongji
 * @time 2025/3/14 13:09
 * @description
 */
@SpringBootTest
@Slf4j
public class ThreadPoolTaskExecutorTests {
    @Resource
    private ThreadPoolTaskExecutor executor;

    @Test
    void testSubmit() {
        executor.submit(() -> log.info("异步线程中说：刘同骥是大帅哥"));
    }
}
