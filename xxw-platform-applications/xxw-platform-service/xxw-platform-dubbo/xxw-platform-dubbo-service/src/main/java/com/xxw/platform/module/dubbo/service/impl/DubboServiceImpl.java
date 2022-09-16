package com.xxw.platform.module.dubbo.service.impl;

import com.xxw.platform.dubbo.api.order.OrderDubboApi;
import com.xxw.platform.dubbo.api.order.dto.OrderDubboDTO;
import com.xxw.platform.module.dubbo.dao.intf.XxwOrderDao;
import com.xxw.platform.module.dubbo.entity.XxwOrderEntity;
import com.xxw.platform.module.dubbo.service.DubboService;
import com.xxw.platform.module.util.rest.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

@Slf4j
@org.apache.dubbo.config.annotation.DubboService
public class DubboServiceImpl implements OrderDubboApi, DubboService {

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

    @Override
    public Result<String> buyOrder(OrderDubboDTO dto) {
        Integer id = dto.getId();
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(id);
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao0.save(entity);
        xxwOrderDao1.save(entity);
        return Result.success();
    }
}
