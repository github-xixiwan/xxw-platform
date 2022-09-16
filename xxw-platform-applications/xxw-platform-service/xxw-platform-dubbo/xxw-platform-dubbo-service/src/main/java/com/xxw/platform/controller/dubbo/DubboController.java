package com.xxw.platform.controller.dubbo;

import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "dubbo")
@RequestMapping("/dubbo")
@RefreshScope
@RestController
public class DubboController {

    @Value("${name:word}")
    private String name;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }
}
