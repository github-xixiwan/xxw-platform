package com.xxw.platform.api.order;

import com.xxw.platform.api.order.model.dto.SeckillDTO;
import com.xxw.platform.util.rest.Result;

public interface OrderApi {

    Result<String> hello();

    Result<String> addSeckill(SeckillDTO dto);

}
