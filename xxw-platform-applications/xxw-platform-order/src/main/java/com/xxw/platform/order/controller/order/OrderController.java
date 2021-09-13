package com.xxw.platform.order.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxw.platform.api.order.model.dto.SeckillDTO;
import com.xxw.platform.order.module.order.model.entity.XxwOrder;
import com.xxw.platform.order.module.order.service.IXxwOrderService;
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

    @Resource
    private IXxwOrderService xxwOrderService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return orderService.hello();
    }

    @PostMapping("/addOrder")
    public Result<String> addOrder(@RequestBody XxwOrder dto) {
        xxwOrderService.save(dto);
        return Result.success();
    }

    @PostMapping("/page")
    public Result<Page<XxwOrder>> page(@RequestBody XxwOrder dto) {
        LambdaQueryWrapper<XxwOrder> wrapper = new LambdaQueryWrapper<XxwOrder>()
                .eq(dto.getUserId() != null, XxwOrder::getUserId, dto.getUserId());
        return Result.success(xxwOrderService.page(new Page<>(), wrapper));
    }

    @PostMapping("/addSeckill")
    public Result<String> addSeckill(@RequestBody SeckillDTO dto) {
        orderService.addSeckill(dto);
        return Result.success();
    }
}
