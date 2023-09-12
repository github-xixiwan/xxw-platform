package com.xxw.platform.starter.mqtt.service;

public interface MqttService {

    void addTopic(String topic, int qos);

    void removeTopic(String topic);
}
