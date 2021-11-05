package com.xxw.platform.starter.mqtt.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Map;

@Slf4j
public class MqttMessageCallBack implements MqttCallback {

    private Map<String, IMqttMessageListener> topicFilterListeners;

    public MqttMessageCallBack(Map<String, IMqttMessageListener> topicFilterListeners) {
        this.topicFilterListeners = topicFilterListeners;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        for (Map.Entry<String, IMqttMessageListener> listenerEntry : topicFilterListeners.entrySet()) {
            String topicFilter = listenerEntry.getKey();
            if (isMatched(topicFilter, topic)) {
                listenerEntry.getValue().messageArrived(topic, message);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    /**
     * Paho topic matcher does not work with shared subscription topic filter of emqttd
     * https://github.com/eclipse/paho.mqtt.java/issues/367#issuecomment-300100385
     * <p>
     * http://emqtt.io/docs/v2/advanced.html#shared-subscription
     *
     * @param topicFilter the topicFilter for mqtt
     * @param topic       the topic
     * @return boolean for matched
     */
    private boolean isMatched(String topicFilter, String topic) {
        if (topicFilter.startsWith("$queue/")) {
            topicFilter = topicFilter.replaceFirst("\\$queue/", "");
        } else if (topicFilter.startsWith("$share/")) {
            topicFilter = topicFilter.replaceFirst("\\$share/", "");
            topicFilter = topicFilter.substring(topicFilter.indexOf('/'));
        }
        return MqttTopic.isMatched(topicFilter, topic);
    }
}
