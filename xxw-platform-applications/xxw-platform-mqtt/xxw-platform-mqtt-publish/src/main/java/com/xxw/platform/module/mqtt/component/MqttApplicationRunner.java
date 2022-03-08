package com.xxw.platform.module.mqtt.component;

import com.xxw.platform.starter.mqtt.service.MqttService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MqttApplicationRunner implements ApplicationRunner {

    @Resource
    private MqttService mqttService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mqttService.addTopic("$queue/defaultTopic3", 0);
        mqttService.addTopic("$queue/willTopic", 1);
        mqttService.addTopic("$queue/onlineTopic", 1);
    }
}
