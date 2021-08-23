package com.xxw.platform.order.module.order.stream.produce;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.annotation.Resource;

@Service
public class OrderProduce {

    private static final String BINDING_NAME = "add-order";

    @Resource
    private StreamBridge streamBridge;

    public void addOrder(String body) {
        Message<String> message = MessageBuilder.withPayload(body)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        streamBridge.send(BINDING_NAME, message);
    }
}
