package com.tongji.xiaohashu.note.biz.consumer;

import com.alibaba.nacos.shaded.com.google.common.util.concurrent.RateLimiter;
import com.tongji.framework.common.util.JsonUtils;
import com.tongji.xiaohashu.note.biz.constant.MQConstants;
import com.tongji.xiaohashu.note.biz.domain.dataobject.NoteCollectionDO;
import com.tongji.xiaohashu.note.biz.domain.mapper.NoteCollectionDOMapper;
import com.tongji.xiaohashu.note.biz.model.dto.CollectUnCollectNoteMqDTO;
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
 * @time 2025/4/2 14:23
 * @description
 */
@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COLLECT_OR_UN_COLLECT, // Group 组
        topic = MQConstants.TOPIC_COLLECT_OR_UN_COLLECT, // 消费的主题 Topic
        consumeMode = ConsumeMode.ORDERLY // 设置为顺序消费模式
)
@Slf4j
public class CollectUnCollectNoteConsumer implements RocketMQListener<Message> {
    private final RateLimiter rateLimiter = RateLimiter.create(5000);

    @Resource
    private NoteCollectionDOMapper noteCollectionDOMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(Message message) {
        rateLimiter.acquire();

        // 幂等性: 通过联合唯一索引保证

        String bodyJsonStr = new String(message.getBody());
        String tags = message.getTags();

        log.info("==> CollectUnCollectNoteConsumer 消费了消息 {}, tags: {}", bodyJsonStr, tags);

        if (Objects.equals(tags, MQConstants.TAG_COLLECT)) { // 收藏笔记
            handleCollectNoteTagMessage(bodyJsonStr);
        } else if (Objects.equals(tags, MQConstants.TAG_UN_COLLECT)) { // 取消收藏笔记
            handleUnCollectNoteTagMessage(bodyJsonStr);
        }
    }

    /**
     * 笔记收藏
     */
    private void handleCollectNoteTagMessage(String bodyJsonStr) {
        CollectUnCollectNoteMqDTO collectUnCollectNoteMqDTO = JsonUtils.parseObject(bodyJsonStr, CollectUnCollectNoteMqDTO.class);
        if (Objects.isNull(collectUnCollectNoteMqDTO)) {
            return;
        }

        Long userId = collectUnCollectNoteMqDTO.getUserId();
        Long noteId = collectUnCollectNoteMqDTO.getNoteId();
        Integer type = collectUnCollectNoteMqDTO.getType();
        LocalDateTime createTime = collectUnCollectNoteMqDTO.getCreateTime();

        NoteCollectionDO noteCollectionDO = NoteCollectionDO.builder()
                .userId(userId)
                .noteId(noteId)
                .createTime(createTime)
                .status(type)
                .build();

        int count = noteCollectionDOMapper.insertOrUpdate(noteCollectionDO);
        if (count == 0) return;

        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(bodyJsonStr).build();

        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_COLLECT, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【计数: 笔记收藏】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【计数: 笔记收藏】MQ 发送异常: ", throwable);
            }
        });
    }

    /**
     * 笔记取消收藏
     */
    private void handleUnCollectNoteTagMessage(String bodyJsonStr) {
        CollectUnCollectNoteMqDTO collectUnCollectNoteMqDTO = JsonUtils.parseObject(bodyJsonStr, CollectUnCollectNoteMqDTO.class);
        if (Objects.isNull(collectUnCollectNoteMqDTO)) {
            return;
        }

        Long userId = collectUnCollectNoteMqDTO.getUserId();
        Long noteId = collectUnCollectNoteMqDTO.getNoteId();
        Integer type = collectUnCollectNoteMqDTO.getType();
        LocalDateTime createTime = collectUnCollectNoteMqDTO.getCreateTime();

        NoteCollectionDO noteCollectionDO = NoteCollectionDO.builder()
                .userId(userId)
                .noteId(noteId)
                .createTime(createTime)
                .status(type)
                .build();

        int count = noteCollectionDOMapper.update2UnCollectByUserIdAndNoteId(noteCollectionDO);
        if (count == 0) return;

        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(bodyJsonStr).build();

        rocketMQTemplate.asyncSend(MQConstants.TOPIC_COUNT_NOTE_COLLECT, message, new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> 【计数: 笔记取消收藏】MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> 【计数: 笔记取消收藏】MQ 发送异常: ", throwable);
            }
        });
    }
}
