package com.xxw.platform.module.single.stream.consume;

import com.xxw.platform.module.single.model.entity.XxwOrder;
import com.xxw.platform.module.single.service.IXxwOrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Consumer;

@Service
@RefreshScope
public class RocketmqReceive {

    @Value("${name:word}")
    private String name;

    @Resource
    private IXxwOrderService xxwOrderService;

    @Bean
    public Consumer<XxwOrder> addOrder() {
        return message -> {
            try {
                System.out.println(name + "消费新增订单: " + message);
                xxwOrderService.save(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Consumer<XxwOrder> addOrders() {
        return message -> {
            try {
                System.out.println(name + "11111111111111111111: " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
