package com.xxw.platform.module.single.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxw.platform.module.single.model.entity.XxwOrder;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xxw
 * @since 2021-09-06
 */
public interface IXxwOrderService extends IService<XxwOrder> {

    String buyOrder(String orderId);
}
