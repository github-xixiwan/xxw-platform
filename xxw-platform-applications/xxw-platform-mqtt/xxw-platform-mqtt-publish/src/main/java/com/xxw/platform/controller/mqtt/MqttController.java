package com.xxw.platform.controller.mqtt;

import com.xxw.platform.module.mqtt.dto.MqttSendDTO;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import com.xxw.platform.starter.mqtt.service.MqttService;
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

    ;

    @PostMapping("/removeTopic")
    public Result<Void> removeTopic(@RequestBody MqttSendDTO dto) {
        String topic = dto.getTopic();
        mqttService.removeTopic(topic);
        return Result.success();
    }

    ;

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
            String json = "";
        }
        return Result.success();
    }
}
