package com.xxw.platform.order.module.order.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    /**
     * 订单编号
     */
    private Long id;

    /**
     * 运单号
     */
    private String waybillId;

    /**
     * 订单录入时间
     */
    private LocalDateTime inputTime;
}
