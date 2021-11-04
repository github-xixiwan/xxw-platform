package com.xxw.platform.controller.mqtt;

import com.xxw.platform.module.mqtt.model.dto.MqttSendDTO;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "mqtt相关业务")
@RequestMapping("/mqtt")
@RestController
public class MqttController {

    @PostMapping("/send")
    public Result<Void> send(@RequestBody MqttSendDTO dto) {
        return Result.success();
    }
}
