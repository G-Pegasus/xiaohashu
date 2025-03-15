package com.tongji.xiaohashu.auth.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.tongji.xiaohashu.auth.alarm.AlarmInterface;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tongji
 * @time 2025/3/15 19:12
 * @description
 */
@RestController
@Slf4j
public class TestController {
    @NacosValue(value = "${rate-limit.api.limit}", autoRefreshed = true)
    private Integer limit;

    @Resource
    private AlarmInterface alarm;

    @GetMapping("/test")
    public String test() {
        return "当前限流阈值为: " + limit;
    }

    @GetMapping("/alarm")
    public String sendAlarm() {
        alarm.send("系统出错啦，犬小哈这个月绩效没了，速度上线解决问题！");
        return "alarm success";
    }
}
