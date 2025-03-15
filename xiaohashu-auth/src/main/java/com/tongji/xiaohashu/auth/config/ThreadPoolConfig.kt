package com.tongji.xiaohashu.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
open class ThreadPoolConfig {
    @Bean(name = ["taskExecutor"])
    open fun taskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        // 核心线程数
        executor.corePoolSize = 10
        // 最大线程数
        executor.maxPoolSize = 50
        // 队列容量
        executor.queueCapacity = 200
        // 活跃时间
        executor.keepAliveSeconds = 30
        // 线程名前缀
        executor.threadNamePrefix = "AuthExecutor-"

        // 拒绝策略 当线程池达到最大线程数并且队列已满时，任务会被拒绝。
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true)
        // 设置等待时间，如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是被没有完成的任务阻塞
        executor.setAwaitTerminationSeconds(60)

        executor.initialize()
        return executor
    }
}