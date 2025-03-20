package com.tongji.framework.common.util;

import java.util.regex.Pattern;

/**
 * @author tongji
 * @time 2025/3/19 11:29
 * @description 参数条件工具
 */
public class ParamUtils {
    private ParamUtils() {}

    // ============================== 校验昵称 ==============================
    // 定义昵称长度范围
    private static final int NICK_NAME_MIN_LENGTH = 2;
    private static final int NICK_NAME_MAX_LENGTH = 24;
    // 定义特殊字符的正则表达式
    private static final String NICK_NAME_REGEX = "[!@#$%^&*(),.?\":{}|<>]";

    /**
     * 昵称校验
     */
    public static boolean checkNickname(String nickname) {
        // 检查长度
        if (nickname.length() < NICK_NAME_MIN_LENGTH || nickname.length() > NICK_NAME_MAX_LENGTH) {
            return false;
        }

        // 检查是否含有特殊字符
        Pattern pattern = Pattern.compile(NICK_NAME_REGEX);
        return !pattern.matcher(nickname).find();
    }

    // ============================== 校验小哈书号 ==============================
    // 定义 ID 长度范围
    private static final int ID_MIN_LENGTH = 6;
    private static final int ID_MAX_LENGTH = 15;

    // 定义正则表达式
    private static final String ID_REGEX = "^[a-zA-Z0-9_]+$";

    /**
     * 小哈书 ID 校验
     */
    public static boolean checkXiaohashuId(String xiaohashuId) {
        // 检查长度
        if (xiaohashuId.length() < ID_MIN_LENGTH || xiaohashuId.length() > ID_MAX_LENGTH) {
            return false;
        }
        // 检查格式
        Pattern pattern = Pattern.compile(ID_REGEX);
        return pattern.matcher(xiaohashuId).matches();
    }

    /**
     * 字符串长度校验
     */
    public static boolean checkLength(String str, int length) {
        // 检查长度
        return !str.isEmpty() && str.length() <= length;
    }
}
