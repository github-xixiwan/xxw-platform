package com.xxw.platform;

import com.xxw.platform.starter.mqtt.service.MqttService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xxw.platform"})
public class MqttSubscribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttSubscribeApplication.class, args);
    }

    @Resource
    private MqttService mqttService;

    @PostConstruct
    public void subscribe() {
        mqttService.addTopic("defaultTopic1", 0);
    }

}
