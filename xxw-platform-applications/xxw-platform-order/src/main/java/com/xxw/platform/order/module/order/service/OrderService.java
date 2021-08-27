package com.xxw.platform.order.module.order.service;

import com.xxw.platform.api.order.OrderApi;
import com.xxw.platform.api.order.model.dto.SeckillDTO;
import com.xxw.platform.api.pay.PayApi;
import com.xxw.platform.api.pay.model.dto.SuccessKilledDTO;
import com.xxw.platform.order.module.order.model.entity.XxwSeckill;
import com.xxw.platform.order.module.order.stream.produce.OrderProduce;
import com.xxw.platform.util.json.JsonUtil;
import com.xxw.platform.util.rest.Result;
import io.seata.spring.annotation.GlobalTransactional;
import ma.glasnost.orika.MapperFacade;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@DubboService
@RefreshScope
public class OrderService implements OrderApi {

    @Value("${name:word}")
    private String name;

    @DubboReference
    private PayApi payApi;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private IXxwSeckillService seckillService;

    @Resource
    private OrderProduce orderProduce;

    @Override
    public Result<String> hello() {
        return Result.success(name + ":" + payApi.hello().getData());
    }

    @Override
    @GlobalTransactional
    public Result<String> addSeckill(SeckillDTO dto) {
        XxwSeckill seckill = mapperFacade.map(dto, XxwSeckill.class);
        seckill.setNumber(1);
        seckill.setStartTime(LocalDateTime.now());
        seckill.setEndTime(LocalDateTime.now());
        seckill.setCreateTime(LocalDateTime.now());
        seckill.setVersion(1);
        seckillService.save(seckill);

        SuccessKilledDTO successKilledDTO = new SuccessKilledDTO();
        successKilledDTO.setSeckillId(seckill.getSeckillId());
        successKilledDTO.setUserId(111111L);
        successKilledDTO.setState(0);
        successKilledDTO.setCreateTime(LocalDateTime.now());
        payApi.addSuccessKilled(successKilledDTO);
        orderProduce.addOrder(JsonUtil.toJson(successKilledDTO));
        return Result.success();
    }

}
