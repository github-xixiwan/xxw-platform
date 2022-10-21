package com.xxw.platform.module.order.service;

import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.util.rest.Result;

public interface OrderService {

    Result<String> buyOrder0(OrderDTO dto);

    Result<String> buyOrder1(OrderDTO dto);
}
