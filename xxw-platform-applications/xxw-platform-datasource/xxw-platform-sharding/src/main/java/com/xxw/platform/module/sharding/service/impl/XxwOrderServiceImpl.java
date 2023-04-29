package com.xxw.platform.module.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.sharding.dto.OrderDTO;
import com.xxw.platform.module.sharding.entity.XxwOrder;
import com.xxw.platform.module.sharding.mapper.XxwOrderMapper;
import com.xxw.platform.module.sharding.service.IXxwOrderService;
import com.xxw.platform.module.util.rest.Result;
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
public class XxwOrderServiceImpl extends ServiceImpl<XxwOrderMapper, XxwOrder> implements IXxwOrderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> buyOrder(OrderDTO dto) {
        Integer id = dto.getId();
        XxwOrder xxwOrder = new XxwOrder();
        xxwOrder.setId(id);
        xxwOrder.setOrderSn(String.valueOf(id));
        save(xxwOrder);
        return Result.success();
    }
}
