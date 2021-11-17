package com.xxw.platform.controller.mqtt;

import com.xxw.platform.module.mqtt.model.dto.MqttSendDTO;
import com.xxw.platform.starter.mqtt.publish.IMqttPublish;
import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        log.info("消息内容：{}", JsonUtil.toJson(dto));
        String topic = dto.getTopic();
        int qos = dto.getQos();
        String payload = dto.getPayload();
        if (StringUtils.isNotBlank(topic) && qos >= 0 && StringUtils.isNotBlank(payload)) {
            log.info("发布自定义主题，自定义机制");
            mqttPublish.sendToMqtt(topic, qos, payload);
        } else if (StringUtils.isNotBlank(topic) && StringUtils.isNotBlank(payload)) {
            log.info("发布自定义主题，默认机制");
            mqttPublish.sendToMqtt(topic, payload);
        } else if (StringUtils.isNotBlank(payload)) {
            log.info("发布默认主题");
            mqttPublish.sendToMqtt(payload);
        }
        return Result.success();
    }
}
