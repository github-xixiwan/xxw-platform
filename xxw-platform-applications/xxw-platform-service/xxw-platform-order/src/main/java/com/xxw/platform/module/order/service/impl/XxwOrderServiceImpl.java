package com.xxw.platform.module.order.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.order.dto.OrderDTO;
import com.xxw.platform.module.order.entity.XxwOrder;
import com.xxw.platform.module.order.mapper.XxwOrderMapper;
import com.xxw.platform.module.order.service.IXxwOrderService;
import com.xxw.platform.module.common.rest.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxw
 * @since 2023-04-28
 */
@Service
@DS("ds0")
public class XxwOrderServiceImpl extends ServiceImpl<XxwOrderMapper, XxwOrder> implements IXxwOrderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> buyOrder(OrderDTO dto) {
        Integer id = dto.getId();
        XxwOrder xxwOrder = new XxwOrder();
        xxwOrder.setOrderSn(String.valueOf(id));
        save(xxwOrder);
        return Result.success();
    }
}
