package com.xxw.platform.module.mqtt.runner;

import com.xxw.platform.module.common.constants.Constants;
import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import com.xxw.platform.starter.mqtt.service.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MqttApplicationRunner implements ApplicationRunner {

    @Value("${spring.mqtt.clientId}")
    private String clientId;

    @Resource
    private MqttService mqttService;

    @Resource
    private IMqttPublish mqttPublish;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            String payload = "[" + clientId + "] 已连接...";
            mqttPublish.sendToMqtt(Constants.CommonConstant.ONLINE_TOPIC, 1, payload);

//            //共享订阅
//            mqttService.addTopic("$queue/sharedSubscription", 1);
//            //共享群组订阅
//            mqttService.addTopic("$share/222/sharedGroupSubscription", 1);
        } catch (Exception e) {
            log.error("mqtt 错误：{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
