package com.xxw.platform.module.rocketmq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxw.platform.module.rocketmq.mapper.XxwOrderMapper;
import com.xxw.platform.module.rocketmq.model.entity.XxwOrder;
import com.xxw.platform.module.rocketmq.service.IXxwOrderService;
import com.xxw.platform.module.rocketmq.event.MsgDataEvent;
import com.xxw.platform.module.rocketmq.event.OrderDataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xxw
 * @since 2021-09-06
 */
@Slf4j
@Service
public class XxwOrderServiceImpl extends ServiceImpl<XxwOrderMapper, XxwOrder> implements IXxwOrderService {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public String buyOrder(String orderId) {
        applicationContext.publishEvent(new OrderDataEvent(orderId));
        applicationContext.publishEvent(new MsgDataEvent("订单ID：" + orderId));
        return "下单成功";
    }
}
