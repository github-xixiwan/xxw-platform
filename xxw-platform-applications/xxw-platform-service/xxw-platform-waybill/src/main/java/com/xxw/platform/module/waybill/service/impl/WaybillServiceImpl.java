package com.xxw.platform.module.waybill.service.impl;

import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.dao.intf.XxwOrderDao;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import com.xxw.platform.module.waybill.entity.XxwOrderEntity;
import com.xxw.platform.module.waybill.service.WaybillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class WaybillServiceImpl implements WaybillService {

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

    @Override
    public Result<String> buyOrder(WaybillDTO dto) {
        Integer id = dto.getId();
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(id);
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao0.save(entity);
        xxwOrderDao1.save(entity);
        return Result.success();
    }
}
