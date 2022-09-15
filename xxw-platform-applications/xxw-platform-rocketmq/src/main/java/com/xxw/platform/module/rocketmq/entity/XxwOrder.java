package com.xxw.platform.module.rocketmq.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author xxw
 * @since 2021-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "XxwOrder对象", description = "")
public class XxwOrder implements Serializable {

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

    @ApiModelProperty(value = "实际需要支付的金额")
    private BigDecimal actualPrice;

    private Integer integral;

    private BigDecimal integralMoney;

    @ApiModelProperty(value = "订单总价")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal goodsPrice;

    private LocalDateTime addTime;

    private LocalDateTime confirmTime;

    private LocalDateTime payTime;

    @ApiModelProperty(value = "配送费用")
    private Integer freightPrice;

    @ApiModelProperty(value = "使用的优惠券id")
    private Integer couponId;

    private Integer parentId;

    private BigDecimal couponPrice;

    private String callbackStatus;

    private String shippingNo;

    private BigDecimal fullCutPrice;

    private String orderType;


}
