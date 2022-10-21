package com.xxw.platform.module.waybill.api;

import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "waybill", path = "/waybill")
public interface WaybillApi {

    @PostMapping("/buyOrder0")
    Result<String> buyOrder0(@RequestBody WaybillDTO dto);

    @PostMapping("/buyOrder1")
    Result<String> buyOrder1(@RequestBody WaybillDTO dto);

}
