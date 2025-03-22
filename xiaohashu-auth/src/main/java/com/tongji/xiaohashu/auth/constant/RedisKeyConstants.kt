package com.tongji.xiaohashu.auth.constant

object RedisKeyConstants {
    /**
     * 验证码 KEY 前缀
     */
    private const val VERIFICATION_CODE_KEY_PREFIX = "verification_code:"

    /**
     * 构建验证码 KEY
     */
    @JvmStatic
    fun buildVerificationCodeKey(phone: String): String {
        return VERIFICATION_CODE_KEY_PREFIX + phone
    }
}