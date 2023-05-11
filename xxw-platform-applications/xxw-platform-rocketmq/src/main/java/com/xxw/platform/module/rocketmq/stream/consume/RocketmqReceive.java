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
//            try {
                log.info("消费普通消息：{}", message);
//                System.out.println(1/0);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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
