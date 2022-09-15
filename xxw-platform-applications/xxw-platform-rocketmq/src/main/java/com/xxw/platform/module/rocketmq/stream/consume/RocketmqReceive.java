package com.xxw.platform.module.rocketmq.stream.consume;

import com.xxw.platform.module.rocketmq.entity.XxwOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RefreshScope
public class RocketmqReceive {

    @Value("${name:word}")
    private String name;

    @Bean
    public Consumer<XxwOrder> addOrder() {
        return message -> {
            try {
                System.out.println(name + "消费新增订单: " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Consumer<XxwOrder> addOrders() {
        return message -> {
            try {
                System.out.println(name + "消费新增订单s: " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
