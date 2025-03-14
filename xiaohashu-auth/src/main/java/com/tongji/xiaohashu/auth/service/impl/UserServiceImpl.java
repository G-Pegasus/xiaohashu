package com.tongji.xiaohashu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.tongji.framework.common.enums.DeletedEnum;
import com.tongji.framework.common.enums.StatusEnum;
import com.tongji.framework.common.exception.BizException;
import com.tongji.framework.common.response.Response;
import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.auth.constant.RedisKeyConstants;
import com.tongji.xiaohashu.auth.constant.RoleConstants;
import com.tongji.xiaohashu.auth.domain.dataobject.UserDO;
import com.tongji.xiaohashu.auth.domain.dataobject.UserRoleDO;
import com.tongji.xiaohashu.auth.domain.mapper.UserDOMapper;
import com.tongji.xiaohashu.auth.domain.mapper.UserRoleDOMapper;
import com.tongji.xiaohashu.auth.enums.LoginTypeEnum;
import com.tongji.xiaohashu.auth.enums.ResponseCodeEnum;
import com.tongji.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import com.tongji.xiaohashu.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/15 11:04
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserRoleDOMapper userRoleDOMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        Long userId = null;

        // 判断登录类型
        switch (Objects.requireNonNull(loginTypeEnum)) {
            case VERIFICATION_CODE:
                String verificationCode = userLoginReqVO.getCode();

                // 校验入参验证码是否为空
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "验证码不能为空");

                // 构建验证码 Redis Key
                String key = RedisKeyConstants.buildVerificationCodeKey(phone);
                // 查询存储在 Redis 中该用户的登录验证码
                String sentCode = (String) redisTemplate.opsForValue().get(key);

                // 判断用户提交的验证码，与 Redis 中的验证码是否一致
                if (!StringUtils.equals(verificationCode, sentCode)) {
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                // 通过手机号查询记录
                UserDO userDO = userDOMapper.selectByPhone(phone);

                log.info("==> 用户是否注册, phone: {}, userDO: {}", phone, JsonUtils.toJsonString(userDO));

                if (Objects.isNull(userDO)) {
                    // 若此用户还没有注册，系统自动注册该用户
                    userId = registerUser(phone);
                } else {
                    userId = userDO.getId();
                }
                break;
            case PASSWORD:
                // TODO 密码登录逻辑
                break;
            default:
                break;
        }

        // SaToken 登录用户，入参为用户 ID
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return Response.success(tokenInfo.tokenValue);
    }

    // 系统自动注册用户
    // 该注解保证方法块内代码的原子性，要么全部成功，要么全部失败；
    // 声明式事务
    /*@Transactional(rollbackFor = Exception.class)
    public Long registerUser1(String phone) {
        // 获取全局自增的小哈书 ID
        Long xiaohashuId =  redisTemplate.opsForValue().increment(RedisKeyConstants.XIAOHASHU_ID_GENERATOR_KEY);

        UserDO userDO = UserDO.builder()
                .phone(phone)
                .xiaohashuId(String.valueOf(xiaohashuId))
                .nickname("小红薯" + xiaohashuId)
                .status(StatusEnum.ENABLE.getValue())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        // 添加入库
        userDOMapper.insert(userDO);
        Long userId = userDO.getId();

        // 给该用户一个默认的角色
        UserRoleDO userRoleDO = UserRoleDO.builder()
                .userId(userId)
                .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .isDeleted(DeletedEnum.NO.getValue())
                .build();
        userRoleDOMapper.insert(userRoleDO);

        // 将该用户的角色 ID 存入 Redis 中
        List<Long> roles = Lists.newArrayList();
        roles.add(RoleConstants.COMMON_USER_ROLE_ID);
        String userRolesKey = RedisKeyConstants.buildUserRoleKey(phone);
        redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

        return userId;
    }*/

    // 编程式事务
    private Long registerUser(String phone) {
        return transactionTemplate.execute(status -> {
            try {
                // 获取全局自增的小哈书 ID
                Long xiaohashuId = redisTemplate.opsForValue().increment(RedisKeyConstants.XIAOHASHU_ID_GENERATOR_KEY);

                UserDO userDO = UserDO.builder()
                        .phone(phone)
                        .xiaohashuId(String.valueOf(xiaohashuId)) // 自动生成小红书号 ID
                        .nickname("小红薯" + xiaohashuId) // 自动生成昵称, 如：小红薯10000
                        .status(StatusEnum.ENABLE.getValue()) // 状态为启用
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue()) // 逻辑删除
                        .build();

                // 添加入库
                userDOMapper.insert(userDO);
                // 获取刚刚添加入库的用户 ID
                Long userId = userDO.getId();

                // 给该用户分配一个默认角色
                UserRoleDO userRoleDO = UserRoleDO.builder()
                        .userId(userId)
                        .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .isDeleted(DeletedEnum.NO.getValue())
                        .build();
                userRoleDOMapper.insert(userRoleDO);

                // 将该用户的角色 ID 存入 Redis 中
                List<Long> roles = Lists.newArrayList();
                roles.add(RoleConstants.COMMON_USER_ROLE_ID);
                String userRolesKey = RedisKeyConstants.buildUserRoleKey(phone);
                redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(roles));

                return userId;
            } catch (Exception e) {
                status.setRollbackOnly(); // 标记事务为回滚
                log.error("==> 系统注册用户异常: ", e);
                return null;
            }
        });
    }
}
