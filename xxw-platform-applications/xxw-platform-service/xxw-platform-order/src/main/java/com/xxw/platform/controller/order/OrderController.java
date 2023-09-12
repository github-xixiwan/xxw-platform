package com.xxw.platform.controller.order;

import com.xxw.platform.module.order.api.OrderApi;
import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.order.service.IXxwOrderService;
import com.xxw.platform.module.common.rest.Result;
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
    private IXxwOrderService xxwOrderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @Override
    @PostMapping("/buyOrder")
    public Result<String> buyOrder(@RequestBody OrderDTO dto) {
        xxwOrderService.buyOrder(dto);
        return Result.success(name);
    }

}
