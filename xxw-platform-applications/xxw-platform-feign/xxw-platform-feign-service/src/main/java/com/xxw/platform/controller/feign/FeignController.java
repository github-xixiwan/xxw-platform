package com.xxw.platform.controller.feign;

import com.xxw.platform.feign.api.order.model.dto.OrderFeignDTO;
import com.xxw.platform.module.feign.model.entity.XxwOrder;
import com.xxw.platform.module.feign.service.IXxwOrder0Service;
import com.xxw.platform.module.feign.service.IXxwOrder1Service;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "feign")
@RequestMapping("/feign")
@RestController
public class FeignController {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IXxwOrder0Service xxwOrder0Service;

    @Resource
    private IXxwOrder1Service xxwOrder1Service;

    @PostMapping("/order")
    public Result<String> order(@RequestBody OrderFeignDTO dto) {
        xxwOrder0Service.save(mapperFacade.map(dto, XxwOrder.class));
        xxwOrder1Service.save(mapperFacade.map(dto, XxwOrder.class));
        return Result.success(name);
    }
}