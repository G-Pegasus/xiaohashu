package com.tongji.xiaohashu.auth.alarm

import com.tongji.xiaohashu.auth.alarm.impl.MailAlarmHelper
import com.tongji.xiaohashu.auth.alarm.impl.SmsAlarmHelper
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author tongji
 * @time 2025/3/15 19:15
 * @description
 */
@Configuration
@RefreshScope
open class AlarmConfig {
    @Value("\${alarm.type}")
    private val alarmType: String? = null

    @Bean
    @RefreshScope
    open fun alarmHelper(): AlarmInterface {
        // 根据配置文件的告警类型，初始化选择不同的告警实现类
        return if (StringUtils.equals("sms", alarmType)) {
            SmsAlarmHelper()
        } else if (StringUtils.equals("mail", alarmType)) {
            MailAlarmHelper()
        } else {
            throw IllegalArgumentException("错误的告警信息...")
        }
    }
}