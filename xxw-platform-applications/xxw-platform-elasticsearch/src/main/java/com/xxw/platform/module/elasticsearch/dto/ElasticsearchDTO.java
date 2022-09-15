package com.xxw.platform.module.elasticsearch.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "XxwOrderDTO", description = "")
public class ElasticsearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderSn;

    private Long userId;

    private Integer orderStatus;

    private Integer shippingStatus;

    private Integer payStatus;

    private String consignee;

    private String country;

    private String province;

    private String city;

    private String district;

    private String address;

    private String mobile;

    private String postscript;

    private Integer shippingId;

    private String shippingName;

    private String payId;

    private String payName;

    private BigDecimal shippingFee;

    private BigDecimal actualPrice;

    private Integer integral;

    private BigDecimal integralMoney;

    private BigDecimal orderPrice;

    private BigDecimal goodsPrice;

    private LocalDateTime addTime;

    private LocalDateTime confirmTime;

    private LocalDateTime payTime;

    private Integer freightPrice;

    private Integer couponId;

    private Integer parentId;

    private BigDecimal couponPrice;

    private String callbackStatus;

    private String shippingNo;

    private BigDecimal fullCutPrice;

    private String orderType;


}
