package com.xxw.platform.module.rocketmq.stream.produce;

import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public void normalMessage(RocketmqDTO dto) {
        String orderSn = dto.getOrderSn();
        Message<RocketmqDTO> message = MessageBuilder.withPayload(dto)
                //设置消息索引键，可根据关键字精确查找某条消息
                .setHeader(MessageConst.PROPERTY_KEYS, orderSn).build();
        streamBridge.send("normal-message", message);
        log.info("发送普通消息：{}", dto);
    }

    public void delayMessage(RocketmqDTO dto) {
        String orderSn = dto.getOrderSn();
        Message<RocketmqDTO> message = MessageBuilder.withPayload(dto)
                //设置消息索引键，可根据关键字精确查找某条消息
                .setHeader(MessageConst.PROPERTY_KEYS, orderSn)
                //设置延时等级1~18 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4).build();
        streamBridge.send("delay-message", message);
        log.info("发送延时消息：{}", dto);
    }

    public void filterMessage(RocketmqDTO dto) {
        String orderSn = dto.getOrderSn();
        Long userId = dto.getUserId();
        Message<RocketmqDTO> message = MessageBuilder.withPayload(dto)
                //设置消息索引键，可根据关键字精确查找某条消息
                .setHeader(MessageConst.PROPERTY_KEYS, orderSn)
                //设置消息Tag，用于消费端根据指定Tag过滤消息
                .setHeader(MessageConst.PROPERTY_TAGS, userId).build();
        streamBridge.send("filter-message", message);
        log.info("发送过滤消息：{}", dto);
    }
}
