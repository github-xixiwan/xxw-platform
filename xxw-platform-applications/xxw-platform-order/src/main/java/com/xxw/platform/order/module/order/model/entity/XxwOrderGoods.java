package com.xxw.platform.order.module.order.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

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
@ApiModel(value="XxwOrderGoods对象", description="")
public class XxwOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单Id")
    private Integer orderId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品序列号")
    private String goodsSn;

    @ApiModelProperty(value = "产品Id")
    private Integer productId;

    @ApiModelProperty(value = "商品数量")
    private Integer number;

    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "商品规格详情")
    private String goodsSpecifitionNameValue;

    @ApiModelProperty(value = "虚拟商品")
    private Integer isReal;

    @ApiModelProperty(value = "商品规格Ids")
    private String goodsSpecifitionIds;

    @ApiModelProperty(value = "图片链接")
    private String listPicUrl;


}
