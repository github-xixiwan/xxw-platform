package com.xxw.platform.module.rocketmq.stream.produce;

import com.google.common.collect.Maps;
import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public void normalMessage(RocketmqDTO dto) {
        streamBridge.send("normal-message", dto);
        log.info("发送普通消息：{}", dto);
    }

    public void delayMessage(RocketmqDTO dto) {
        Map<String, Object> headers = Maps.newHashMap();
        // 设置延时等级1~18 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        headers.put(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 4);
        Message<RocketmqDTO> message = MessageBuilder.createMessage(dto, new MessageHeaders(headers));
        streamBridge.send("delay-message", message);
        log.info("发送延时消息：{}", dto);
    }

}
