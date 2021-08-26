package com.xxw.platform.order.controller.order;

import com.xxw.platform.api.order.model.dto.SeckillDTO;
import com.xxw.platform.order.module.order.service.OrderService;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "订单相关业务")
@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return orderService.hello();
    }

    @PostMapping("/addSeckill")
    public Result<String> addSeckill(@RequestBody SeckillDTO dto) {
        orderService.addSeckill(dto);
        return Result.success();
    }
}
