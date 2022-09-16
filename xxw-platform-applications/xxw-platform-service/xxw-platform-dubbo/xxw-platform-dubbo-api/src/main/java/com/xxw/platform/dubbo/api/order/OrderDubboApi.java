package com.xxw.platform.dubbo.api.order;

import com.xxw.platform.dubbo.api.order.dto.OrderDubboDTO;
import com.xxw.platform.module.util.rest.Result;

public interface OrderDubboApi {

    Result<String> buyOrder(OrderDubboDTO dto);

}
