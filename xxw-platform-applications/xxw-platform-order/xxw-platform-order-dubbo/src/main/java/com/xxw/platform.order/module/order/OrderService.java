package com.xxw.platform.order.module.order;

import com.xxw.platform.order.api.OrderApi;
import com.xxw.platform.pay.api.PayApi;
import com.xxw.platform.util.result.Result;
import com.xxw.platform.waybill.api.WaybillApi;
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
