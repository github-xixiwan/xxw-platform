package com.xxw.platform.feign.api.order;

import com.xxw.platform.feign.api.order.model.dto.OrderFeignDTO;
import com.xxw.platform.module.util.rest.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feign-service", path = "/feign")
public interface OrderFeignApi {

    @PostMapping("/order")
    Result<String> order(@RequestBody OrderFeignDTO dto);

}
