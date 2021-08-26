package com.xxw.platform.pay.module.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀库存表
 * </p>
 *
 * @author xxw
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="XxwSeckill对象", description="秒杀库存表")
public class XxwSeckill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品库存id")
    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Long seckillId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "库存数量")
    private Integer number;

    @ApiModelProperty(value = "秒杀开启时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "版本号")
    private Integer version;


}
