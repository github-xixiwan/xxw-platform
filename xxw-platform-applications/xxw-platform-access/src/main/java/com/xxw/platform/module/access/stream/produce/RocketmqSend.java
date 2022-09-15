package com.xxw.platform.module.access.stream.produce;

import com.xxw.platform.module.access.entity.XxwOrder;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public void sendOrder(XxwOrder order) {
        streamBridge.send("add-order", order);
        streamBridge.send("add-orders", order);
    }
}
