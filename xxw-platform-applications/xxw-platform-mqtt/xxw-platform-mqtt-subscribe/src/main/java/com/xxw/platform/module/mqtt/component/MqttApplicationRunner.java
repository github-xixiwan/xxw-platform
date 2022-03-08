package com.xxw.platform.module.mqtt.component;

import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MqttApplicationRunner implements ApplicationRunner {

    @Value("${spring.mqtt.subscribe.clientId}")
    private String clientId;

    @Resource
    private IMqttPublish mqttPublish;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String payload = "[" + clientId + "] 已连接...";
        mqttPublish.sendToMqtt("onlineTopic", 1, payload);
    }
}
