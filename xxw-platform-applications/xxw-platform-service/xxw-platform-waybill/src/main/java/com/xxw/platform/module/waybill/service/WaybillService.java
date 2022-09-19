package com.xxw.platform.module.waybill.service;

import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.dto.WaybillDTO;

public interface WaybillService {

    Result<String> buyOrder(WaybillDTO dto);
}
