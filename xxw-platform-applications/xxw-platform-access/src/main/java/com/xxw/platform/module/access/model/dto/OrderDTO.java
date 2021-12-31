package com.xxw.platform.module.access.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderSn;

    private Long userId;
}
