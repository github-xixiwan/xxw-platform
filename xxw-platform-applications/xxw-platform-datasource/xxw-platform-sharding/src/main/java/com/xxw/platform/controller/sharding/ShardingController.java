package com.xxw.platform.controller.sharding;

import com.xxw.platform.module.sharding.dto.OrderDTO;
import com.xxw.platform.module.sharding.entity.XxwOrder;
import com.xxw.platform.module.sharding.service.IXxwOrderService;
import com.xxw.platform.module.common.rest.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/sharding")
@RefreshScope
@RestController
public class ShardingController {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwOrderService xxwOrderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @GetMapping("/buyOrder")
    public Result<String> buyOrder(@RequestParam("id") Integer id) {
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(id);
        xxwOrderService.buyOrder(orderDto);
        return Result.success(name);
    }

    @GetMapping("/listOrder")
    public Result<List<XxwOrder>> listOrder() {
        return Result.success(xxwOrderService.list());
    }
}
