package com.xxw.platform;

import com.xxw.platform.module.mqtt.listener.MqttMessageListener;
import com.xxw.platform.starter.mqtt.callback.MqttMessageCallBack;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, IMqttMessageListener> topicFilterListeners = new HashMap<>();
        IMqttMessageListener mqttMessageListener = new MqttMessageListener();
        topicFilterListeners.put("$queue/mqttTopic", mqttMessageListener);
        mqttClient.setCallback(new MqttMessageCallBack(topicFilterListeners));
        mqttClient.subscribe("$queue/mqttTopic", 2, new MqttMessageListener());
    }

}
