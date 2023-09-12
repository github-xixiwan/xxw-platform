package com.xxw.platform.module.waybill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxw.platform.module.common.rest.Result;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import com.xxw.platform.module.waybill.entity.XxwWaybill;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xxw
 * @since 2023-04-28
 */
public interface IXxwWaybillService extends IService<XxwWaybill> {

    Result<String> buyWaybill(WaybillDTO dto);
}
