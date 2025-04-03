package com.tongji.xiaohashu.note.biz.consumer;

import com.google.common.util.concurrent.RateLimiter;
import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.note.biz.constant.MQConstants;
import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteLikeDO;
import com.tongji.xiaohashu.note.biz.domain.mapper.NoteLikeDOMapper;
import com.tongji.xiaohashu.note.biz.model.dto.LikeUnLikeNoteMqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author tongji
 * @time 2025/3/30 21:34
 * @description
 */
@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_LIKE_OR_UNLIKE,
        topic = MQConstants.TOPIC_LIKE_OR_UNLIKE, consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class LikeUnLikeNoteConsumer implements RocketMQListener<Message> {
    // 每秒创建 5000 个令牌
    private final RateLimiter rateLimiter = RateLimiter.create(5000);

    @Resource
    private NoteLikeDOMapper noteLikeDOMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(Message message) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        // 幂等性: 通过联合唯一索引保证

        // 消息体
        String bodyJsonStr = new String(message.getBody());
        // 标签
        String tags = message.getTags();

        log.info("==> LikeUnlikeNoteConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);

        // 根据 MQ 标签，判断操作类型
        if (Objects.equals(tags, MQConstants.TAG_LIKE)) { // 点赞笔记
            handleLikeNoteTagMessage(bodyJsonStr);
        } else if (Objects.equals(tags, MQConstants.TAG_UNLIKE)) { // 取消点赞笔记
            handleUnlikeNoteTagMessage(bodyJsonStr);
        }
    }

    /**
     * 笔记点赞
     */
    private void handleLikeNoteTagMessage(String bodyJsonStr) {
        LikeUnLikeNoteMqDTO likeNoteMqDTO = JsonUtils.parseObject(bodyJsonStr, LikeUnLikeNoteMqDTO.class);
        if (Objects.isNull(likeNoteMqDTO)) {
            return;
        }

        Long userId = likeNoteMqDTO.getUserId();
        Long noteId = likeNoteMqDTO.getNoteId();
        Integer type = likeNoteMqDTO.getType();
        LocalDateTime createTime = likeNoteMqDTO.getCreateTime();

        NoteLikeDO noteLikeDO = NoteLikeDO.builder()
                .userId(userId)
                .noteId(noteId)
                .createTime(createTime)
                .status(type)
                .build();

        int count = noteLikeDOMapper.insertOrUpdate(noteLikeDO);
        if (count == 0) {
            return;
        }

        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(bodyJsonStr)
                .build();

        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_LIKE, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【计数: 笔记点赞】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【计数: 笔记点赞】MQ 发送异常: ", throwable);
            }
        });
    }

    /**
     * 笔记取消点赞
     */
    private void handleUnlikeNoteTagMessage(String bodyJsonStr) {
        LikeUnLikeNoteMqDTO unLikeNoteMqDTO = JsonUtils.parseObject(bodyJsonStr, LikeUnLikeNoteMqDTO.class);
        if (Objects.isNull(unLikeNoteMqDTO)) {
            return;
        }

        Long userId = unLikeNoteMqDTO.getUserId();
        Long noteId = unLikeNoteMqDTO.getNoteId();
        Integer type = unLikeNoteMqDTO.getType();
        LocalDateTime createTime = unLikeNoteMqDTO.getCreateTime();

        NoteLikeDO noteLikeDO = NoteLikeDO.builder()
                .userId(userId)
                .noteId(noteId)
                .createTime(createTime)
                .status(type)
                .build();

        int count = noteLikeDOMapper.update2UnlikeByUserIdAndNoteId(noteLikeDO);
        if (count == 0) {
            return;
        }

        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(bodyJsonStr)
                .build();

        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_LIKE, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【计数: 笔记取消点赞】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【计数: 笔记取消点赞】MQ 发送异常: ", throwable);
            }
        });
    }
}
