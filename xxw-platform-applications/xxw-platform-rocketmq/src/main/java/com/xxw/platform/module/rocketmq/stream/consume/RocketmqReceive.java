package com.xxw.platform.module.rocketmq.stream.consume;

import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RefreshScope
public class RocketmqReceive {

    @Value("${name:word}")
    private String name;

    @Bean
    public Consumer<RocketmqDTO> normalMessage() {
        return message -> {
            //普通消息（相同组默认顺序消息）
            //5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //最大重试 默认3次（若重试次数超过16次，后面每次重试间隔都为2小时）
            //消息保留天数 3天
            try {
                Long userId = message.getUserId();
                if (userId.equals(2L)) {
                    log.info("1111111：睡眠前");
                    Thread.sleep(10000);
                    log.info("2222222：睡眠后");
                }
                log.info("消费普通消息：{}", message);
//                System.out.println(1/0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Consumer<RocketmqDTO> delayMessage() {
        return message -> {
            try {
                log.info("消费延时消息：{}", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Consumer<RocketmqDTO> filterMessage() {
        return message -> {
            try {
                log.info("消费过滤消息：{}", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
