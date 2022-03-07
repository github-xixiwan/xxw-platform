package com.xxw.platform.starter.mqtt.service.impl;

import com.xxw.platform.starter.mqtt.service.MqttService;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class MqttServiceImpl implements MqttService {

    @Resource
    private MqttPahoMessageDrivenChannelAdapter adapter;

    @Override
    public void addTopic(String topic, int qos) {
        String[] topics = adapter.getTopic();
        if (!Arrays.asList(topics).contains(topic)) {
            adapter.addTopic(topic, qos);
        }
    }

    @Override
    public void removeTopic(String topic) {
        adapter.removeTopic(topic);
    }
}