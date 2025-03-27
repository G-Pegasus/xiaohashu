package com.tongji.xiaohashu.note.biz.consumer;

import com.tongji.xiaohashu.note.biz.constant.MQConstants;
import com.tongji.xiaohashu.note.biz.service.NoteService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author tongji
 * @time 2025/3/26 10:19
 * @description
 */
@Component
@Slf4j
@RocketMQMessageListener(
        consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_DELETE_NOTE_LOCAL_CACHE, // Group
        topic = MQConstants.TOPIC_DELETE_NOTE_LOCAL_CACHE, // 消费者的主题
        messageModel = MessageModel.BROADCASTING) // 广播模式
public class DeleteNoteLocalCacheConsumer implements RocketMQListener<String> {
    @Resource
    private NoteService noteService;

    @Override
    public void onMessage(String body) {
        Long noteId = Long.valueOf(body);
        noteService.deleteNoteLocalCache(noteId);

        log.info("## 消费者消费成功, noteId: {}", noteId);
    }
}
