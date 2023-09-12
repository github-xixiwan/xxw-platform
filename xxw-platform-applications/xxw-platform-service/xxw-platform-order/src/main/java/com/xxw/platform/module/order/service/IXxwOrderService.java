package com.xxw.platform.module.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.order.entity.XxwOrder;
import com.xxw.platform.module.common.rest.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xxw
 * @since 2023-04-28
 */
public interface IXxwOrderService extends IService<XxwOrder> {

    Result<String> buyOrder(OrderDTO dto);
}
