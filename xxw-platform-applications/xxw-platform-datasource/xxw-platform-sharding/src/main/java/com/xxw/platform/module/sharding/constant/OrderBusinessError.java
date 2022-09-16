package com.xxw.platform.module.sharding.constant;

import com.xxw.platform.module.util.exception.ErrorEnumInterface;

/**
 * 订单业务级抛错
 */
public enum OrderBusinessError implements ErrorEnumInterface {

    ORDER_NOT_FOUND_EXCEPTION(409001, "订单未找到");

    private Integer code;

    private String msg;

    OrderBusinessError(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
