package com.xxw.platform.controller.access;

import com.xxw.platform.module.access.model.entity.XxwOrder;
import com.xxw.platform.module.access.stream.produce.RocketmqSend;
import com.xxw.platform.module.api.order.OrderApi;
import com.xxw.platform.module.api.order.model.dto.OrderDTO;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "接入")
@RequestMapping("/access")
@RestController
@RefreshScope
public class AccessController {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private RocketmqSend rocketmqSend;

    @DubboReference
    private OrderApi orderApi;

    @PostMapping("/orderMq")
    public Result<String> orderMq(@RequestBody OrderDTO dto) {
        rocketmqSend.sendOrder(mapperFacade.map(dto, XxwOrder.class));
        return Result.success(name);
    }

    @PostMapping("/orderDubbo")
    public Result<String> order(@RequestBody OrderDTO dto) {
        orderApi.addOrder(dto);
        return Result.success(name);
    }
}
