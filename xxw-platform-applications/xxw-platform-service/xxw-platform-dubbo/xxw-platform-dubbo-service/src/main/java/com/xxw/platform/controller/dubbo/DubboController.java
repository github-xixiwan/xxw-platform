package com.xxw.platform.controller.dubbo;

import com.xxw.platform.module.dubbo.dao.intf.XxwOrderDao;
import com.xxw.platform.module.dubbo.entity.XxwOrderEntity;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "dubbo")
@RequestMapping("/dubbo")
@RefreshScope
@RestController
public class DubboController {

    @Value("${name:word}")
    private String name;

    @Resource
    @Qualifier("xxwOrder0DaoImpl")
    private XxwOrderDao xxwOrderDao0;

    @Resource
    @Qualifier("xxwOrder1DaoImpl")
    private XxwOrderDao xxwOrderDao1;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @GetMapping("/buyOrder")
    public Result<String> buyOrder(@RequestParam("orderId") String orderId) {
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(Integer.parseInt(orderId));
        entity.setOrderSn(orderId);
        xxwOrderDao0.save(entity);
        xxwOrderDao1.save(entity);
        return Result.success(name);
    }
}
