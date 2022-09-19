package com.xxw.platform.controller.access;

import com.xxw.platform.module.order.api.OrderApi;
import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.api.WaybillApi;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RequestMapping("/access")
@RestController
@RefreshScope
public class AccessController {

    @Value("${name:word}")
    private String name;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private OrderApi orderApi;

    @Resource
    private WaybillApi waybillApi;

    @PostMapping("/order")
    public Result<String> order(@RequestBody OrderDTO dto) {
        orderApi.buyOrder(dto);
        return Result.success(name);
    }

    @PostMapping("/waybill")
    public Result<String> waybill(@RequestBody WaybillDTO dto) {
        waybillApi.buyOrder(dto);
        return Result.success(name);
    }
}
