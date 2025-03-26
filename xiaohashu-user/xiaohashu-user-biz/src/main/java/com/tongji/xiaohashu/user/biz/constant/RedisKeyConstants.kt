package com.tongji.xiaohashu.user.biz.constant

object RedisKeyConstants {
    /**
     * 小哈书全局 ID 生成器 KEY
     */
    const val XIAOHASHU_ID_GENERATOR_KEY: String = "xiaohashu.id.generator"

    /**
     * 用户角色数据 KEY 前缀
     */
    private const val USER_ROLES_KEY_PREFIX = "user:roles:"

    /**
     * 用户对应的角色集合 KEY
     */
    @JvmStatic
    fun buildUserRoleKey(userId: Long) = "$USER_ROLES_KEY_PREFIX$userId"

    /**
     * 角色对应的权限集合 KEY 前缀
     */
    private const val ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:"


    /**
     * 构建角色对应的权限集合 KEY
     */
    @JvmStatic
    fun buildRolePermissionsKey(roleKey: String) = "$ROLE_PERMISSIONS_KEY_PREFIX$roleKey"

    /**
     * 用户信息数据 KEY 前缀
     */
    private const val USER_INFO_KEY_PREFIX: String = "user:info:"

    /**
     * 构建角色对应的权限集合 KEY
     */
    @JvmStatic
    fun buildUserInfoKey(userId: Long) = "$USER_INFO_KEY_PREFIX$userId"
}