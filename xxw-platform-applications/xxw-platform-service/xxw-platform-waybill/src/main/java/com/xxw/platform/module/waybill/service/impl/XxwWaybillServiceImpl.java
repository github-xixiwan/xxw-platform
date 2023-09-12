package com.xxw.platform.module.waybill.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.common.rest.Result;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import com.xxw.platform.module.waybill.entity.XxwWaybill;
import com.xxw.platform.module.waybill.mapper.XxwWaybillMapper;
import com.xxw.platform.module.waybill.service.IXxwWaybillService;
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
@DS("ds1")
public class XxwWaybillServiceImpl extends ServiceImpl<XxwWaybillMapper, XxwWaybill> implements IXxwWaybillService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> buyWaybill(WaybillDTO dto) {
        Integer id = dto.getId();
        XxwWaybill xxwWaybill = new XxwWaybill();
        xxwWaybill.setOrderSn(String.valueOf(id));
        save(xxwWaybill);
        return Result.success();
    }
}
