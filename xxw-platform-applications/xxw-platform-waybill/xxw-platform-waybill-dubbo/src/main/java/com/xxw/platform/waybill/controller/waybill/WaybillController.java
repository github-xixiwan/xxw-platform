package com.xxw.platform.waybill.controller.waybill;

import com.xxw.platform.waybill.module.waybill.WaybillService;
import com.xxw.platform.util.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "运单相关业务")
@RequestMapping("/waybill")
@RestController
public class WaybillController {

    @Resource
    private WaybillService waybillService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return waybillService.hello();
    }
}
