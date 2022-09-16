package com.xxw.platform.module.feign.service;

import com.xxw.platform.feign.api.order.dto.OrderFeignDTO;
import com.xxw.platform.module.util.rest.Result;

public interface FeignService {

    Result<String> buyOrder(OrderFeignDTO dto);
}
