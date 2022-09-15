package com.xxw.platform.controller.dubbo;

import com.xxw.platform.module.dubbo.entity.XxwOrder;
import com.xxw.platform.module.dubbo.service.IXxwOrder0Service;
import com.xxw.platform.module.dubbo.service.IXxwOrder1Service;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "dubbo")
@RequestMapping("/dubbo")
@RefreshScope
@RestController
public class DubboController {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwOrder0Service xxwOrder0Service;

    @Resource
    private IXxwOrder1Service xxwOrder1Service;

    @PostMapping("/order")
    public Result<String> order(@RequestBody XxwOrder order) {
        xxwOrder0Service.save(order);
        xxwOrder1Service.save(order);
        return Result.success(name);
    }
}
