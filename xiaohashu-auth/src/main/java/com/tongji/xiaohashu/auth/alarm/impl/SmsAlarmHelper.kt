package com.tongji.xiaohashu.auth.alarm.impl

import cn.dev33.satoken.SaManager
import com.tongji.xiaohashu.auth.alarm.AlarmInterface

/**
 * @author tongji
 * @time 2025/3/15 19:16
 * @description 短信告警
 */
class SmsAlarmHelper : AlarmInterface {
    override fun send(message: String?): Boolean {
        SaManager.log.info("==> 【短信告警】：{}", message)

        // 业务逻辑...
        return true
    }
}