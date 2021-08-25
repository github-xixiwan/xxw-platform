package com.xxw.platform.waybill.module.waybill.service;

import com.xxw.platform.waybill.module.waybill.dubbo.WaybillApi;
import com.xxw.platform.web.rest.Result;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@DubboService
@RefreshScope
public class WaybillService implements WaybillApi {

    @Value("${name:word}")
    private String name;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

}
