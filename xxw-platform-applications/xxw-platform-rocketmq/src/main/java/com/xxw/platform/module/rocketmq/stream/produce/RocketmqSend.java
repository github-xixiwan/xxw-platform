package com.xxw.platform.module.rocketmq.stream.produce;

import com.xxw.platform.module.rocketmq.dto.RocketmqDTO;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketmqSend {

    @Resource
    private StreamBridge streamBridge;

    public void sendOrder(RocketmqDTO dto) {
        streamBridge.send("add-order", dto);
        streamBridge.send("add-orders", dto);
    }
}
