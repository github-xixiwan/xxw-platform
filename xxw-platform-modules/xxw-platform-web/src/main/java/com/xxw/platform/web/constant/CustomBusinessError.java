package com.xxw.platform.web.constant;

import com.xxw.platform.util.exception.ErrorEnumInterface;

/**
 * 自定义级抛错
 */
public enum CustomBusinessError implements ErrorEnumInterface {

    UNKNOWN_EXCEPTION(1, "未知异常"),
    BUSINESS_EXCEPTION(2, "自定义异常"),
    SYSTEM_EXCEPTION(3, "系统异常"),
    NO_HANDLER_FOUND_EXCEPTION(4, "未找到该资源"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(5, "请求参数异常"),
    REST_CLIENT_EXCEPTION(6, "业务接口异常"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(7, "不支持http媒体类型"),
    Http_Message_Not_Readable_Exception(8, "Http消息不可读异常");

    private Integer code;

    private String msg;

    CustomBusinessError(Integer code, String msg) {
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
