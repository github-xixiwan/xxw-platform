package com.xxw.platform.order.module.order.service;

import com.xxw.platform.api.order.OrderApi;
import com.xxw.platform.api.pay.PayApi;
import com.xxw.platform.util.rest.Result;
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

    @Override
    public Result<String> hello() {
        return Result.success(name + ":" + payApi.hello().getData());
    }

}
