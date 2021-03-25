package com.xxw.platform.pay.module.pay;

import com.xxw.platform.pay.api.PayApi;
import com.xxw.platform.util.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class PayService implements PayApi {

    @Value("${name:word}")
    private String name;

    @Override
    public Result<String> hello() {
        return Result.success(name);
    }

}
