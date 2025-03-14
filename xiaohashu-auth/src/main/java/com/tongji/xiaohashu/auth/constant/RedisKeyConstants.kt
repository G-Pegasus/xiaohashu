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

    /**
     * 小哈书全局 ID 生成器 KEY
     */
    const val XIAOHASHU_ID_GENERATOR_KEY: String = "xiaohashu.id.generator"

    /**
     * 用户角色数据 KEY 前缀
     */
    private const val USER_ROLES_KEY_PREFIX = "user:roles:"

    /**
     * 构建验证码 KEY
     */
    @JvmStatic
    fun buildUserRoleKey(phone: String): String {
        return USER_ROLES_KEY_PREFIX + phone
    }

    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private const val ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:"


    /**
     * 构建角色对应的权限集合 KEY
     */
    @JvmStatic
    fun buildRolePermissionsKey(roleId: Long): String {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId
    }
}