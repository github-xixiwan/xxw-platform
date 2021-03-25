package com.xxw.platform.order.module.order;

import com.xxw.platform.order.api.OrderApi;
import com.xxw.platform.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class OrderService implements OrderApi {

    @Value("${name:word}")
    private String name;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

}
