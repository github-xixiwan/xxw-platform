package com.xxw.platform.order.module.order.service;

import com.xxw.platform.order.module.order.dubbo.OrderApi;
import com.xxw.platform.order.module.order.dubbo.PayApi;
import com.xxw.platform.order.module.order.dubbo.WaybillApi;
import com.xxw.platform.web.rest.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@DubboService
@RefreshScope
public class OrderService implements OrderApi {

    @Value("${name:word}")
    private String name;

    @DubboReference
    private PayApi payApi;

    @DubboReference
    private WaybillApi waybillApi;

    @Override
    public Result<String> hello() {
        return Result.success(name + ":" + payApi.hello().getData() + ":" + waybillApi.hello().getData());
    }

}
