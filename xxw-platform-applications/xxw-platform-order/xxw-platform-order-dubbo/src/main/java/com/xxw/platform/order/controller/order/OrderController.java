package com.xxw.platform.order.controller.order;

import com.xxw.platform.order.module.order.OrderService;
import com.xxw.platform.order.module.order.stream.produce.OrderProduce;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "订单相关业务")
@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderProduce orderProduce;

    @GetMapping("/hello")
    public Result<String> hello() {
        return orderService.hello();
    }

    @GetMapping("/addOrder")
    public Result<String> addOrder(@RequestParam("body") String body) {
        orderProduce.addOrder(body);
        return Result.success();
    }
}
