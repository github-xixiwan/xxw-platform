package com.xxw.platform.module.mqtt.listener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class MqttMessageListener implements IMqttMessageListener {

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String result = new String(message.getPayload(), "UTF-8");
            System.out.println("接收消息主题 : " + topic);
            System.out.println("接收消息Qos : " + message.getQos());
            System.out.println("接收消息内容 : " + result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
