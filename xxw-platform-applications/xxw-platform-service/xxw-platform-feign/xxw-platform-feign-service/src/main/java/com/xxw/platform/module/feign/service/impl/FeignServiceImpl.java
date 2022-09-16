package com.xxw.platform.module.feign.service.impl;

import com.xxw.platform.feign.api.order.dto.OrderFeignDTO;
import com.xxw.platform.module.feign.dao.intf.XxwOrderDao;
import com.xxw.platform.module.feign.entity.XxwOrderEntity;
import com.xxw.platform.module.feign.service.FeignService;
import com.xxw.platform.module.util.rest.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class FeignServiceImpl implements FeignService {

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

    @Override
    public Result<String> buyOrder(OrderFeignDTO dto) {
        Integer id = dto.getId();
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(id);
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao0.save(entity);
        xxwOrderDao1.save(entity);
        return Result.success();
    }
}
