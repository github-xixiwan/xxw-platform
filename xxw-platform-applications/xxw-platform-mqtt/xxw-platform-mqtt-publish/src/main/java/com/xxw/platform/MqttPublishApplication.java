package com.xxw.platform;

import com.xxw.platform.starter.mqtt.service.MqttService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xxw.platform"})
public class MqttPublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttPublishApplication.class, args);
    }

    @Resource
    private MqttService mqttService;

    @PostConstruct
    public void subscribe() {
        mqttService.addTopic("$queue/defaultTopic3", 0);
        mqttService.addTopic("$queue/willTopic", 1);
        mqttService.addTopic("$queue/onlineTopic", 1);
    }

}
