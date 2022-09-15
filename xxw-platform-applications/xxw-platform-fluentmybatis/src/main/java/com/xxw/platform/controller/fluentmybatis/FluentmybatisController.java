package com.xxw.platform.controller.fluentmybatis;

import com.xxw.platform.module.fluentmybatis.dao.intf.XxwOrderDao;
import com.xxw.platform.module.fluentmybatis.entity.XxwOrderEntity;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "fluentmybatis")
@RequestMapping("/fluentmybatis")
@RefreshScope
@RestController
public class FluentmybatisController {

    @Value("${name:word}")
    private String name;

    @Resource
    private XxwOrderDao xxwOrderDao;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @GetMapping("/buyOrder")
    public Result<String> buyOrder(@RequestParam("orderId") String orderId) {
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(Integer.parseInt(orderId));
        entity.setOrderSn(orderId);
        xxwOrderDao.save(entity);
        return Result.success(name);
    }
}
