package com.xxw.platform;

import com.xxw.platform.callback.MqttCallBack;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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
    private MqttClient mqttClient;

    @PostConstruct
    public void subscribe() throws MqttException {
        mqttClient.setCallback(new MqttCallBack());
        mqttClient.subscribe("mqttTopic", 2);
    }

}
