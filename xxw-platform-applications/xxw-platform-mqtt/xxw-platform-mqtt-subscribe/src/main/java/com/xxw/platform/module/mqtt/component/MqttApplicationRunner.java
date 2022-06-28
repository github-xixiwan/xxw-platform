package com.xxw.platform.module.mqtt.component;

import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import com.xxw.platform.starter.mqtt.service.MqttService;
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
    private MqttService mqttService;

    @Resource
    private IMqttPublish mqttPublish;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mqttService.addTopic("test", 1);
        String payload = "[" + clientId + "] 已连接...";
        mqttPublish.sendToMqtt("onlineTopic", 1, payload);
        //共享订阅
        mqttService.addTopic("$queue/sharedSubscription", 1);
        //共享群组订阅
        mqttService.addTopic("$share/111/sharedGroupSubscription", 1);
    }
}
