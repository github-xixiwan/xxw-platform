package com.xxw.platform.module.single.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDataEvent {

    /**
     * 订单ID
     */
    private String orderId;
}
