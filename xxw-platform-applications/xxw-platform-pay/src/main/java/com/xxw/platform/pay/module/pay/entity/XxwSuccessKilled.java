package com.xxw.platform.pay.module.pay.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀成功明细表
 * </p>
 *
 * @author xxw
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="XxwSuccessKilled对象", description="秒杀成功明细表")
public class XxwSuccessKilled implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "秒杀商品id")
    private Long seckillId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "状态标示：-1指无效，0指成功，1指已付款")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
