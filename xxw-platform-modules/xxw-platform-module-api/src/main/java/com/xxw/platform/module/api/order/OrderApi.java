package com.xxw.platform.module.api.order;

import com.xxw.platform.module.api.order.model.dto.OrderDTO;
import com.xxw.platform.module.api.order.model.vo.OrderVO;
import com.xxw.platform.module.util.rest.Result;

import java.util.List;

public interface OrderApi {

    Result<Void> addOrder(OrderDTO dto);

    Result<List<OrderVO>> getOrder(OrderDTO dto);

}
