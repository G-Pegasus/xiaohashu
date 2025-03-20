package com.tongji.xiaohashu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.tongji.framework.biz.context.holder.LoginUserContextHolder;
import com.tongji.framework.common.exception.BizException;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.constant.RedisKeyConstants;
import com.tongji.xiaohashu.auth.enums.LoginTypeEnum;
import com.tongji.xiaohashu.auth.enums.ResponseCodeEnum;
import com.tongji.xiaohashu.auth.model.vo.user.UpdatePasswordReqVO;
import com.tongji.xiaohashu.auth.model.vo.user.UserLoginReqVO;
import com.tongji.xiaohashu.auth.rpc.UserRpcService;
import com.tongji.xiaohashu.auth.service.AuthService;
import com.tongji.xiaohashu.user.dto.resp.FindUserByPhoneRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/15 11:04
 * @description
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRpcService userRpcService;

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone = userLoginReqVO.getPhone();
        Integer type = userLoginReqVO.getType();

        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);
        // 未知的登录类型
        if (Objects.isNull(loginTypeEnum)) {
            throw new BizException(ResponseCodeEnum.LOGIN_TYPE_ERROR);
        }

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

                // RPC: 调用用户服务，注册用户
                Long userIdTmp = userRpcService.registerUser(phone);
                if (Objects.isNull(userIdTmp)) {
                    throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
                }

                userId = userIdTmp;
                break;
            case PASSWORD:
                String password = userLoginReqVO.getPassword();
                // 根据手机号查询(改用RPC调用用户服务)
                // UserDO userDO1 = userDOMapper.selectByPhone(phone);

                // RPC: 调用用户服务，通过手机号查询用户
                FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);

                // 判断手机号是否注册
                if (Objects.isNull(findUserByPhoneRspDTO)) {
                    throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
                }

                // 拿到密文密码
                String encodePassword = findUserByPhoneRspDTO.getPassword();

                // 匹配密码是否一致
                boolean isPasswordMatch = passwordEncoder.matches(password, encodePassword);

                // 如果不正确，则抛出业务异常，提示用户用户名或密码不正确
                if (!isPasswordMatch) {
                    throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
                }

                userId = findUserByPhoneRspDTO.getId();
                break;
            default:
                break;
        }

        // SaToken 登录用户，入参为用户 ID
        StpUtil.login(userId);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        return Response.success(tokenInfo.tokenValue);
    }

    @Override
    public Response<?> logout() {
        Long userId = LoginUserContextHolder.getUserId();

        log.info("==> 用户退出登录, userId: {}", userId);

        // 退出登录（指定用户 ID）
        StpUtil.logout(userId);

        return Response.success();
    }

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO) {
        // 新密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword  = passwordEncoder.encode(newPassword);

        // RPC: 调用用户服务，更新密码
        userRpcService.updatePassword(encodePassword);

        return Response.success();
    }
}
