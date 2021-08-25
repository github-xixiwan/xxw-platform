package com.xxw.platform.waybill.module.waybill.constant;

import com.xxw.platform.util.exception.ErrorEnumInterface;

/**
 * 运单业务级抛错
 */
public enum WaybillBusinessError implements ErrorEnumInterface {

    ORDER_NOT_FOUND_EXCEPTION(409001, "订单未找到");

    private Integer code;

    private String msg;

    WaybillBusinessError(Integer code, String msg) {
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
