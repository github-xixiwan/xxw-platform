package com.xxw.platform.module.order;

import com.xxw.platform.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class OrderService {

    @Value("${name:word}")
    private String name;

    public Result<String> hello() {
        return Result.success(name);
    }

}
