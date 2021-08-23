package com.xxw.platform.pay.module.pay;

import com.xxw.platform.pay.api.PayApi;
import com.xxw.platform.util.rest.Result;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@DubboService
@RefreshScope
public class PayService implements PayApi {

    @Value("${name:word}")
    private String name;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

}
