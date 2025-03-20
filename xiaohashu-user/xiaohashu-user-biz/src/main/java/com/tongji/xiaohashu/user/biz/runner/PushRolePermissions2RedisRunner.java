package com.tongji.xiaohashu.user.biz.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.user.biz.constant.RedisKeyConstants;
import com.tongji.xiaohashu.user.biz.domain.dataobject.PermissionDO;
import com.tongji.xiaohashu.user.biz.domain.dataobject.RoleDO;
import com.tongji.xiaohashu.user.biz.domain.dataobject.RolePermissionDO;
import com.tongji.xiaohashu.user.biz.domain.mapper.PermissionDOMapper;
import com.tongji.xiaohashu.user.biz.domain.mapper.RoleDOMapper;
import com.tongji.xiaohashu.user.biz.domain.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author tongji
 * @time 2025/3/15 14:45
 * @description 推送角色权限数据至 Redis
 */
@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private PermissionDOMapper permissionDOMapper;
    @Resource
    private RolePermissionDOMapper rolePermissionDOMapper;

    // 权限同步标记 Key
    private static final String PUSH_PERMISSION_FLAG = "push.permission.flag";

    @Override
    public void run(ApplicationArguments args) {
        log.info("==> 服务启动，开始同步角色权限数据到 Redis 中...");

        try {
            // 是否能够同步数据: 原子操作，只有在键 PUSH_PERMISSION_FLAG 不存在时，才会设置该键的值为 "1"，并设置过期时间为 1 天
            boolean canPushed = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG, "1", 1, TimeUnit.DAYS));
            // 如果无法同步权限数据
            if (!canPushed) {
                log.warn("==> 角色权限数据已经同步至 Redis 中，不再同步...");
                return;
            }

            // 查询出所有角色
            List<RoleDO> roleDOS = roleDOMapper.selectEnabledList();

            if (CollUtil.isNotEmpty(roleDOS)) {
                // 拿到所有角色ID
                List<Long> roleIds = roleDOS.stream().map(RoleDO::getId).toList();
                // 根据角色ID，批量查询出所有角色对应的权限
                List<RolePermissionDO> rolePermissionDOS = rolePermissionDOMapper.selectByRoleIds(roleIds);
                // 按角色ID分组，每个角色ID对应多个权限ID
                Map<Long, List<Long>> roleIdPermissionIdsMap = rolePermissionDOS.stream().collect(
                  Collectors.groupingBy(
                          RolePermissionDO::getRoleId, // 按 RoleId 进行分组
                          // 提取 PermissionId 并转换为 List
                          Collectors.mapping(RolePermissionDO::getPermissionId, Collectors.toList())
                  )
                );
                /* 上方 Stream 流 等同于下方语句
                Map<Long, List<Long>> roleIdPermissionIdsMap = new HashMap<>();

                for (RolePermissionDO rolePermissionDO : rolePermissionDOS) {
                    Long roleId = rolePermissionDO.getRoleId(); // 获取角色ID
                    Long permissionId = rolePermissionDO.getPermissionId(); // 获取权限ID

                    // 如果 roleId 不在 Map 中，先初始化一个空 List
                    if (!roleIdPermissionIdsMap.containsKey(roleId)) {
                        roleIdPermissionIdsMap.put(roleId, new ArrayList<>());
                    }

                    // 把 permissionId 添加到对应 roleId 的 List 中
                    roleIdPermissionIdsMap.get(roleId).add(permissionId);
                }
                */

                // 查询 APP 端所有被启用的权限
                List<PermissionDO> permissionDOS = permissionDOMapper.selectAppEnabledList();
                // 权限 ID - 权限 DO
                Map<Long, PermissionDO> permissionIdDOMap = permissionDOS.stream().collect(
                  Collectors.toMap(PermissionDO::getId, permissionDO -> permissionDO)
                );
                /* 上方 Stream 流 等同于下方语句
                Map<Long, PermissionDO> permissionIdDOMap = new HashMap<>();

                for (PermissionDO permissionDO : permissionDOS) {
                    Long permissionId = permissionDO.getId(); // 获取权限 ID
                    permissionIdDOMap.put(permissionId, permissionDO); // 存入 Map
                }
                */

                // 组织 角色-权限 关系
                Map<String, List<String>> roleKeyPermissionMap = Maps.newHashMap();

                roleDOS.forEach(roleDO -> {
                    Long roleId = roleDO.getId();
                    // 当前角色 RoleKey
                    String roleKey = roleDO.getRoleKey();
                    // 当前角色ID对应的权限ID集合
                    List<Long> permissionIds  = roleIdPermissionIdsMap.get(roleId);
                    if (CollUtil.isNotEmpty(permissionIds)) {
                        List<String> permissionKeys = Lists.newArrayList();
                        permissionIds.forEach(permissionId -> {
                            PermissionDO permissionDO = permissionIdDOMap.get(permissionId);
                            permissionKeys.add(permissionDO.getPermissionKey());
                        });
                        roleKeyPermissionMap.put(roleKey, permissionKeys);
                    }
                });

                // 同步至 Redis 中，方便后续网关查询鉴权使用
                roleKeyPermissionMap.forEach((roleId, perDOS) -> {
                    String key = RedisKeyConstants.buildRolePermissionsKey(roleId);
                    redisTemplate.opsForValue().set(key, JsonUtils.toJsonString(perDOS));
                });
            }

            log.info("==> 服务启动，成功同步角色权限数据到 Redis 中...");
        } catch (Exception e) {
            log.error("==> 同步角色权限数据到 Redis 中失败: ", e);
        }
    }
}
