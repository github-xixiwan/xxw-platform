package com.xxw.platform.controller.sharding;

import com.xxw.platform.module.sharding.dao.intf.XxwOrderDao;
import com.xxw.platform.module.sharding.entity.XxwOrderEntity;
import com.xxw.platform.module.util.rest.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "分库分表")
@RequestMapping("/sharding")
@RefreshScope
@RestController
public class ShardingController {

    @Value("${name:word}")
    private String name;

    @Resource
    private XxwOrderDao xxwOrderDao;

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success(name);
    }

    @GetMapping("/buyOrder")
    public Result<String> buyOrder(@RequestParam("id") Integer id) {
        XxwOrderEntity entity = new XxwOrderEntity();
        entity.setId(id);
        entity.setOrderSn(String.valueOf(id));
        xxwOrderDao.save(entity);
        return Result.success(name);
    }
}
