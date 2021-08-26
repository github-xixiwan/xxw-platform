package com.xxw.platform.pay.controller.pay;

import com.xxw.platform.pay.module.pay.entity.XxwSeckill;
import com.xxw.platform.pay.module.pay.service.IXxwSeckillService;
import com.xxw.platform.pay.module.pay.service.PayService;
import com.xxw.platform.util.rest.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "支付相关业务")
@RequestMapping("/pay")
@RestController
public class PayController {

    @Resource
    private PayService payService;

    @Resource
    private IXxwSeckillService seckillService;

    @GetMapping("/hello")
    public Result<String> hello() {
        return payService.hello();
    }

    @GetMapping("/getById")
    public Result<XxwSeckill> getById(@RequestParam("id") Long id) {
        return Result.success(seckillService.getById(id));
    }
}
