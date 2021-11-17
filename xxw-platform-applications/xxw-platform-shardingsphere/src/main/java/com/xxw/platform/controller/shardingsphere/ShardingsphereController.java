package com.xxw.platform.controller.shardingsphere;

import com.xxw.platform.module.shardingsphere.model.entity.XxwOrder;
import com.xxw.platform.module.shardingsphere.service.IXxwOrderService;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "分库分表")
@RequestMapping("/shardingsphere")
@RestController
public class ShardingsphereController {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwOrderService xxwOrderService;

    @PostMapping("/order")
    public Result<String> order(@RequestBody XxwOrder order) {
        xxwOrderService.save(order);
        return Result.success(name);
    }
}
