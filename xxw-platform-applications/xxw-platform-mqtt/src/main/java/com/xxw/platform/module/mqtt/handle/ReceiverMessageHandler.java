package com.xxw.platform.module.mqtt.handle;

import cn.hutool.json.JSONUtil;
import com.xxw.platform.starter.mqtt.config.MqttConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ReceiverMessageHandler {

    @Bean
    @ServiceActivator(inputChannel = MqttConfig.MQTT_INPUT_CHANNEL)
    public MessageHandler handleMessage() {
        return message -> {
            System.out.println("subscribe:" + message.getPayload());
            System.out.println("headers" + JSONUtil.toJsonStr(message.getHeaders()));
        };
    }
}
