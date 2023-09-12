package com.xxw.platform.module.order.api;

import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.common.rest.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order", path = "/order")
public interface OrderApi {

    @PostMapping("/buyOrder")
    Result<String> buyOrder(@RequestBody OrderDTO dto);

}
