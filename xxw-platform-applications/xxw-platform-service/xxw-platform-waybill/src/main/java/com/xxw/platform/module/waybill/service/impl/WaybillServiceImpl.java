package com.xxw.platform.module.waybill.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.dao.intf.XxwOrderDao;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import com.xxw.platform.module.waybill.entity.XxwOrderEntity;
import com.xxw.platform.module.waybill.service.WaybillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @DS("master")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> buyOrder0(WaybillDTO dto) {
        Integer id = dto.getId();
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao0.save(entity);
        return Result.success();
    }

    @Override
    @DS("slave")
    @Transactional(rollbackFor = Exception.class)
    public Result<String> buyOrder1(WaybillDTO dto) {
        Integer id = dto.getId();
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao1.save(entity);
        return Result.success();
    }
}
