package com.xxw.platform.controller.mqtt;

import com.xxw.platform.module.mqtt.dto.MqttSendDTO;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import com.xxw.platform.starter.mqtt.service.MqttService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/mqtt")
@RestController
public class MqttController {

    @Resource
    private MqttService mqttService;

    @Resource
    private IMqttPublish mqttPublish;

    @PostMapping("/addTopic")
    public Result<Void> addTopic(@RequestBody MqttSendDTO dto) {
        String topic = dto.getTopic();
        int qos = dto.getQos();
        mqttService.addTopic(topic, qos);
        return Result.success();
    }

    @PostMapping("/removeTopic")
    public Result<Void> removeTopic(@RequestBody MqttSendDTO dto) {
        String topic = dto.getTopic();
        mqttService.removeTopic(topic);
        return Result.success();
    }

    @PostMapping("/send")
    public Result<Void> send(@RequestBody MqttSendDTO dto) {
        String topic = dto.getTopic();
        int qos = dto.getQos();
        String payload = dto.getPayload();
        mqttPublish.sendToMqtt(topic, qos, payload);
        return Result.success();
    }
}
