package com.xxw.platform.dubbo.api.order;

import com.xxw.platform.dubbo.api.order.model.dto.OrderDubboDTO;
import com.xxw.platform.dubbo.api.order.model.vo.OrderDubboVO;
import com.xxw.platform.module.util.rest.Result;

import java.util.List;

public interface OrderDubboApi {

    Result<Void> addOrder(OrderDubboDTO dto);

    Result<List<OrderDubboVO>> getOrder(OrderDubboDTO dto);

}
