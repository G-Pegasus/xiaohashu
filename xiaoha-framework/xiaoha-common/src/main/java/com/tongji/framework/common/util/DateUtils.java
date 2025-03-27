package com.tongji.framework.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author tongji
 * @time 2025/3/26 21:29
 * @description 日期工具类
 */
public class DateUtils {
    /**
     * LocalDateTime 转时间戳
     */
    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
