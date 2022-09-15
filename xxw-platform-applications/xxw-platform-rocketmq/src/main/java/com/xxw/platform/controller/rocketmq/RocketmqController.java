package com.xxw.platform.controller.rocketmq;

import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import com.xxw.platform.module.rocketmq.stream.produce.RocketmqSend;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "rocketmq")
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

    @PostMapping("/orderMq")
    public Result<String> orderMq(@RequestBody RocketmqDTO dto) {
        rocketmqSend.sendOrder(dto);
        return Result.success(name);
    }
}
