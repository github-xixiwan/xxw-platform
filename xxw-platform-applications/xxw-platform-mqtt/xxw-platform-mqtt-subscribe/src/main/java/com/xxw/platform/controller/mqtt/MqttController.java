package com.xxw.platform.controller.mqtt;

import com.xxw.platform.module.mqtt.model.dto.MqttSendDTO;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "mqtt相关业务")
@RequestMapping("/mqtt")
@RestController
public class MqttController {

    @Resource
    private IMqttPublish mqttPublish;

    @PostMapping("/send")
    public Result<Void> send(@RequestBody MqttSendDTO dto) {
        String topic = dto.getTopic();
        int qos = dto.getQos();
        String payload = dto.getPayload();
        int size = dto.getSize();
        for (int i = 0; i < size; i++) {
//            try {
//                Thread.sleep(100L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mqttPublish.sendToMqtt(topic, qos, payload);
        }
        return Result.success();
    }
}
