package com.xxw.platform.module.dynamic.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * XxwOrderEntity: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Data
@Accessors(
    chain = true
)
@EqualsAndHashCode(
    callSuper = false
)
@AllArgsConstructor
@NoArgsConstructor
@FluentMybatis(
    table = "xxw_order",
    schema = "xxw-order-0"
)
public class XxwOrderEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  @TableId("id")
  private Integer id;

  @TableField(
      value = "actual_price",
      desc = "实际需要支付的金额"
  )
  private BigDecimal actualPrice;

  @TableField("add_time")
  private Date addTime;

  @TableField("address")
  private String address;

  @TableField("callback_status")
  private String callbackStatus;

  @TableField("city")
  private String city;

  @TableField("confirm_time")
  private Date confirmTime;

  @TableField("consignee")
  private String consignee;

  @TableField("country")
  private String country;

  @TableField(
      value = "coupon_id",
      desc = "使用的优惠券id"
  )
  private Integer couponId;

  @TableField("coupon_price")
  private BigDecimal couponPrice;

  @TableField("district")
  private String district;

  @TableField(
      value = "freight_price",
      desc = "配送费用"
  )
  private Integer freightPrice;

  @TableField("full_cut_price")
  private BigDecimal fullCutPrice;

  @TableField(
      value = "goods_price",
      desc = "商品总价"
  )
  private BigDecimal goodsPrice;

  @TableField("integral")
  private Integer integral;

  @TableField("integral_money")
  private BigDecimal integralMoney;

  @TableField("mobile")
  private String mobile;

  @TableField(
      value = "order_price",
      desc = "订单总价"
  )
  private BigDecimal orderPrice;

  @TableField("order_sn")
  private String orderSn;

  @TableField("order_status")
  private Integer orderStatus;

  @TableField("order_type")
  private String orderType;

  @TableField("parent_id")
  private Integer parentId;

  @TableField("pay_id")
  private String payId;

  @TableField("pay_name")
  private String payName;

  @TableField("pay_status")
  private Integer payStatus;

  @TableField("pay_time")
  private Date payTime;

  @TableField("postscript")
  private String postscript;

  @TableField("province")
  private String province;

  @TableField("shipping_fee")
  private BigDecimal shippingFee;

  @TableField("shipping_id")
  private Integer shippingId;

  @TableField("shipping_name")
  private String shippingName;

  @TableField("shipping_no")
  private String shippingNo;

  @TableField("shipping_status")
  private Integer shippingStatus;

  @TableField("user_id")
  private Integer userId;

  @Override
  public final Class entityClass() {
    return XxwOrderEntity.class;
  }
}
