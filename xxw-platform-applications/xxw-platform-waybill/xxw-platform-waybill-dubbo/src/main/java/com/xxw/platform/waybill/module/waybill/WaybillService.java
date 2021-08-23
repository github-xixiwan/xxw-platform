package com.xxw.platform.waybill.module.waybill;

import com.xxw.platform.util.rest.Result;
import com.xxw.platform.waybill.api.WaybillApi;
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
