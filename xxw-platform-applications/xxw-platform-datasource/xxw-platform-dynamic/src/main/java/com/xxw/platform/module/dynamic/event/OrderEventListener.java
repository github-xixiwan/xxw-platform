package com.xxw.platform.module.dynamic.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    @EventListener(OrderDataEvent.class)
    public void order(OrderDataEvent event) {
        Integer id = event.getId();
        log.info("下单------> {}", id);
    }
}
