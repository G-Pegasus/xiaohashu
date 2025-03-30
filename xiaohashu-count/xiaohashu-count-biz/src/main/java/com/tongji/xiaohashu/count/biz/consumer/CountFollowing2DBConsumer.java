package com.tongji.xiaohashu.count.biz.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.count.biz.constant.MQConstants;
import com.tongji.xiaohashu.count.biz.domain.mapper.UserCountDOMapper;
import com.tongji.xiaohashu.count.biz.enums.FollowUnfollowTypeEnum;
import com.tongji.xiaohashu.count.biz.model.dto.CountFollowUnfollowMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/28 15:31
 * @description 计数: 关注数存库
 */
@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COUNT_FOLLOWING_2_DB,
        topic = MQConstants.TOPIC_COUNT_FOLLOWING_2_DB)
@Slf4j
public class CountFollowing2DBConsumer implements RocketMQListener<String> {
    @Resource
    private UserCountDOMapper userCountDOMapper;

    // 每秒创建 5000 个令牌
    private RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        rateLimiter.acquire();
        log.info("## 消费到了 MQ 【计数: 关注数入库】, {}...", body);

        if (StringUtils.isBlank(body)) {
            return;
        }

        CountFollowUnfollowMqDTO countFollowUnfollowMqDTO = JsonUtils.parseObject(body, CountFollowUnfollowMqDTO.class);

        // 操作类型：关注 or 取关
        assert countFollowUnfollowMqDTO != null;
        Integer type = countFollowUnfollowMqDTO.getType();
        Long userId = countFollowUnfollowMqDTO.getUserId();

        // 关注数
        int count = Objects.equals(type, FollowUnfollowTypeEnum.FOLLOW.getCode()) ? 1 : -1;
        // 判断数据库中，若原用户的记录不存在，则插入；若记录已存在，则直接更新
        userCountDOMapper.insertOrUpdateFollowingTotalByUserId(count, userId);
    }
}
