package com.tongji.xiaohashu.auth.alarm

/**
 * @author tongji
 * @time 2025/3/15 19:15
 * @description
 */
interface AlarmInterface {
    // 发送告警信息
    fun send(message: String?): Boolean
}