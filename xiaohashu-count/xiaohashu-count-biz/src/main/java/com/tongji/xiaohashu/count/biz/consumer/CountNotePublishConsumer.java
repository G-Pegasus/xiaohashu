package com.tongji.xiaohashu.count.biz.consumer;

import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.count.biz.constant.MQConstants;
import com.tongji.xiaohashu.count.biz.constant.RedisKeyConstants;
import com.tongji.xiaohashu.count.biz.domain.mapper.UserCountDOMapper;
import com.tongji.xiaohashu.count.biz.model.dto.NoteOperateMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author tongji
 * @time 2025/4/3 11:25
 * @description
 */
@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_NOTE_OPERATE, // Group 组
        topic = MQConstants.TOPIC_NOTE_OPERATE // 主题 Topic
)
@Slf4j
public class CountNotePublishConsumer implements RocketMQListener<Message> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserCountDOMapper userCountDOMapper;

    @Override
    public void onMessage(Message message) {
        String bodyJsonStr = new String(message.getBody());
        String tags = message.getTags();

        log.info("==> CountNotePublishConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);

        // 根据 MQ 标签，判断笔记操作类型
        if (Objects.equals(tags, MQConstants.TAG_NOTE_PUBLISH)) { // 笔记发布
            handleTagMessage(bodyJsonStr, 1);
        } else if (Objects.equals(tags, MQConstants.TAG_NOTE_DELETE)) { // 笔记删除
            handleTagMessage(bodyJsonStr, -1);
        }
    }

    /**
     * 笔记发布，删除
     */
    private void handleTagMessage(String bodyJsonStr, long count) {
        NoteOperateMqDTO noteOperateMqDTO = JsonUtils.parseObject(bodyJsonStr, NoteOperateMqDTO.class);
        if (Objects.isNull(noteOperateMqDTO)) {
            return;
        }

        Long creatorId = noteOperateMqDTO.getCreatorId();

        String countUserRedisKey = RedisKeyConstants.buildCountUserKey(creatorId);
        boolean isCountUserExisted = redisTemplate.hasKey(countUserRedisKey);

        // 若存在才会更新
        // (因为缓存设有过期时间，考虑到过期后，缓存会被删除，这里需要判断一下，存在才会去更新，而初始化工作放在查询计数来做)
        if (isCountUserExisted) {
            // 对目标用户 Hash 中的笔记发布总数，进行加减操作
            redisTemplate.opsForHash().increment(countUserRedisKey, RedisKeyConstants.FIELD_NOTE_TOTAL, count);
        }

        userCountDOMapper.insertOrUpdateNoteTotalByUserId(count, creatorId);
    }
}
