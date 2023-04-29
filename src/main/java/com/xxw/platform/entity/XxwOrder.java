package com.xxw.platform.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "xxw_order")
public class XxwOrder implements Serializable {

    
    @Id(keyType = KeyType.Auto)
    private Integer id;

    
    private String orderSn;

    
    private Integer userId;

    
    private Long orderStatus;

    
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

    /**
     * 实际需要支付的金额
     */
    private BigDecimal actualPrice;

    
    private Long integral;

    
    private BigDecimal integralMoney;

    /**
     * 订单总价
     */
    private BigDecimal orderPrice;

    /**
     * 商品总价
     */
    private BigDecimal goodsPrice;

    
    private LocalDateTime addTime;

    
    private LocalDateTime confirmTime;

    
    private LocalDateTime payTime;

    /**
     * 配送费用
     */
    private Long freightPrice;

    /**
     * 使用的优惠券id
     */
    private Integer couponId;

    
    private Integer parentId;

    
    private BigDecimal couponPrice;

    
    private String callbackStatus;

    
    private String shippingNo;

    
    private BigDecimal fullCutPrice;

    
    private String orderType;

}
