package com.xxw.platform.controller.order;

import com.xxw.platform.module.order.OrderService;
import com.xxw.platform.util.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
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
}
