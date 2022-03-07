package com.xxw.platform.module.mqtt.handle;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ReceiverMessageHandler {

    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handleMessage() {
        return message -> {
            System.out.println("subscribe:" + message.getPayload());
        };
    }
}
