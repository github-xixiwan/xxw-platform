package com.xxw.platform.module.order;

import com.xxw.platform.util.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class OrderService {

    public Result<String> hello() {
        return Result.success("word");
    }

}
