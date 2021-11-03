package com.xxw.platform.module.order.stream.consume;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class OrderConsume {

    @Bean
    public Function<Flux<Message<String>>, Mono<Void>> addOrder() {
        return flux -> flux.map(message -> {
            System.out.println("消费新增订单: " + message.getPayload());
            return message;
        }).then();
    }
}
