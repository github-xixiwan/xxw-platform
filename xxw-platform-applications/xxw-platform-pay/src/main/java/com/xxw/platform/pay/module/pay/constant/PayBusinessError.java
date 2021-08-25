package com.xxw.platform.pay.module.pay.constant;

import com.xxw.platform.util.exception.ErrorEnumInterface;

/**
 * 支付业务级抛错
 */
public enum PayBusinessError implements ErrorEnumInterface {

    ORDER_NOT_FOUND_EXCEPTION(409001, "订单未找到");

    private Integer code;

    private String msg;

    PayBusinessError(Integer code, String msg) {
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
