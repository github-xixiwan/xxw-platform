package com.xxw.platform.module.waybill.api;

import com.xxw.platform.module.common.rest.Result;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "waybill", path = "/waybill")
public interface WaybillApi {

    @PostMapping("/buyWaybill")
    Result<String> buyWaybill(@RequestBody WaybillDTO dto);

}
