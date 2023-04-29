package com.xxw.platform.controller.waybill;

import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.waybill.api.WaybillApi;
import com.xxw.platform.module.waybill.dto.WaybillDTO;
import com.xxw.platform.module.waybill.service.IXxwWaybillService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/waybill")
@RefreshScope
@RestController
public class WaybillController implements WaybillApi {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwWaybillService xxwWaybillService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @Override
    @PostMapping("/buyWaybill")
    public Result<String> buyWaybill(@RequestBody WaybillDTO dto) {
        xxwWaybillService.buyWaybill(dto);
        return Result.success(name);
    }
}
