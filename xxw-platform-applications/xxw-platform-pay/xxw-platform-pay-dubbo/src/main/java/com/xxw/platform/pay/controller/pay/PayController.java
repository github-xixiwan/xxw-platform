package com.xxw.platform.pay.controller.pay;

import com.xxw.platform.pay.module.pay.PayService;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "支付相关业务")
@RequestMapping("/pay")
@RestController
public class PayController {

    @Resource
    private PayService payService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return payService.hello();
    }
}
