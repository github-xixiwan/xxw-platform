package com.xxw.platform.controller.rocketmq;

import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import com.xxw.platform.module.rocketmq.stream.produce.RocketmqSend;
import com.xxw.platform.module.common.rest.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/rocketmq")
@RefreshScope
@RestController
public class RocketmqController {

    @Resource
    private RocketmqSend rocketmqSend;

    @Value("${name:word}")
    private String name;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @PostMapping("/normalMessage")
    public Result<String> normalMessage(@RequestBody RocketmqDTO dto) {
        rocketmqSend.normalMessage(dto);
        return Result.success(name);
    }

    @PostMapping("/delayMessage")
    public Result<String> delayMessage(@RequestBody RocketmqDTO dto) {
        rocketmqSend.delayMessage(dto);
        return Result.success(name);
    }

    @PostMapping("/filterMessage")
    public Result<String> filterMessage(@RequestBody RocketmqDTO dto) {
        rocketmqSend.filterMessage(dto);
        return Result.success(name);
    }
}
