package com.xxw.platform.module.dynamic.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDataEvent {

    /**
     * 订单ID
     */
    private Integer id;
}
