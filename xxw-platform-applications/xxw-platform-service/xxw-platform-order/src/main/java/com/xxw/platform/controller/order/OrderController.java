package com.xxw.platform.controller.order;

import com.xxw.platform.module.order.api.OrderApi;
import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.order.service.OrderService;
import com.xxw.platform.module.util.rest.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/order")
@RefreshScope
@RestController
public class OrderController implements OrderApi {

    @Value("${name:word}")
    private String name;

    @Resource
    private OrderService orderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @Override
    @PostMapping("/buyOrder0")
    public Result<String> buyOrder0(@RequestBody OrderDTO dto) {
        orderService.buyOrder0(dto);
        return Result.success(name);
    }

    @Override
    @PostMapping("/buyOrder1")
    public Result<String> buyOrder1(@RequestBody OrderDTO dto) {
        orderService.buyOrder1(dto);
        return Result.success(name);
    }
}
