package com.xxw.platform.module.single.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgEventListener {

    @EventListener(MsgDataEvent.class)
    public void msg(MsgDataEvent event) {
        String msg = event.getMsg();
        log.info("发消息------> {}", msg);
    }
}
