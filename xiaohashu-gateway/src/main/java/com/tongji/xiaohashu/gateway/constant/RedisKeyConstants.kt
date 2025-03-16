package com.tongji.xiaohashu.gateway.constant

/**
 * @author tongji
 * @time 2025/3/16 14:15
 * @description Redis连接
 */
object RedisKeyConstants {
    /**
     * 用户角色数据 KEY 前缀
     */
    private const val USER_ROLES_KEY_PREFIX = "user:roles:"

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
     * 构建用户-角色 KEY
     */
    @JvmStatic
    fun buildUserRoleKey(userId: Long) = "$USER_ROLES_KEY_PREFIX$userId"
}