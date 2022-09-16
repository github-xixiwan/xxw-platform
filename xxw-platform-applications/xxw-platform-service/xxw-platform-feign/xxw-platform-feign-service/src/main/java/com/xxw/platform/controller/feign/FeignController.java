package com.xxw.platform.controller.feign;

import com.xxw.platform.feign.api.order.dto.OrderFeignDTO;
import com.xxw.platform.module.feign.service.FeignService;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "feign")
@RequestMapping("/feign")
@RefreshScope
@RestController
public class FeignController {

    @Value("${name:word}")
    private String name;

    @Resource
    private FeignService feignService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @PostMapping("/buyOrder")
    public Result<String> buyOrder(@RequestBody OrderFeignDTO dto) {
        feignService.buyOrder(dto);
        return Result.success(name);
    }
}
