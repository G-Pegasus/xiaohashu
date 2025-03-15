package com.tongji.xiaohashu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.tongji.framework.common.exception.BizException;
import com.tongji.framework.common.response.Response;
import com.tongji.xiaohashu.auth.constant.RedisKeyConstants;
import com.tongji.xiaohashu.auth.enums.ResponseCodeEnum;
import com.tongji.xiaohashu.auth.model.vo.veriticationcode.SendVerificationCodeReqVO;
import com.tongji.xiaohashu.auth.service.VerificationCodeService;
import com.tongji.xiaohashu.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author tongji
 * @time 2025/3/14 11:20
 * @description
 */
@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        // 手机号
        String phone = sendVerificationCodeReqVO.getPhone();

        // 构建验证码 redis key
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);

        // 判断是否已经发送验证码
        boolean isSent = redisTemplate.hasKey(key);
        if (isSent) {
            // 若之前发送的验证码未过期，则提示发送频繁
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 生成6位随机数字验证码
        String verificationCode = RandomUtil.randomNumbers(6);

        log.info("==> 手机号: {}, 已发送验证码：【{}】", phone, verificationCode);

        // 调用第三方短信发送服务
        threadPoolTaskExecutor.submit(() -> {
            String signName = "阿里云短信测试";
            String templateCode = "SMS_154950909";
            String templateParam = String.format("{\"code\":\"%s\"}", verificationCode);
            aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
        });

        // 存储验证码到 redis, 并设置过期时间为3分钟
        redisTemplate.opsForValue().set(key, verificationCode, 3, TimeUnit.MINUTES);

        return Response.success();
    }
}
