package com.xxw.platform.controller.access;

import com.xxw.platform.dubbo.api.order.OrderDubboApi;
import com.xxw.platform.dubbo.api.order.dto.OrderDubboDTO;
import com.xxw.platform.feign.api.order.OrderFeignApi;
import com.xxw.platform.feign.api.order.dto.OrderFeignDTO;
import com.xxw.platform.module.access.dto.OrderDTO;
import com.xxw.platform.module.util.rest.Result;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "接入")
@RequestMapping("/access")
@RestController
@RefreshScope
public class AccessController {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @DubboReference
    private OrderDubboApi orderDubboApi;

    @Resource
    private OrderFeignApi orderFeignApi;

    @PostMapping("/orderDubbo")
    public Result<String> orderDubbo(@RequestBody OrderDubboDTO dto) {
        orderDubboApi.buyOrder(dto);
        return Result.success(name);
    }

    @PostMapping("/orderFeign")
    public Result<String> orderFeign(@RequestBody OrderFeignDTO dto) {
        orderFeignApi.buyOrder(dto);
        return Result.success(name);
    }

    @PostMapping("/orderSeata")
    @GlobalTransactional
    public Result<String> orderSeata(@RequestBody OrderDTO dto) {
        orderDubboApi.buyOrder(mapperFacade.map(dto, OrderDubboDTO.class));
        orderFeignApi.buyOrder(mapperFacade.map(dto, OrderFeignDTO.class));
        System.out.println(1 / 0);
        return Result.success(name);
    }
}
