package com.xxw.platform.module.web.constant;

import com.xxw.platform.module.common.exception.ErrorEnumInterface;

/**
 * 自定义级抛错
 */
public enum CustomBusinessError implements ErrorEnumInterface {

    UNKNOWN_EXCEPTION(1, "未知异常"),
    BUSINESS_EXCEPTION(2, "自定义异常"),
    ELASTICSEARCH_EXCEPTION(3, "ELASTICSEARCH异常"),
    SYSTEM_EXCEPTION(4, "系统异常"),
    NO_HANDLER_FOUND_EXCEPTION(5, "未找到该资源"),
    MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(6, "请求参数异常"),
    REST_CLIENT_EXCEPTION(7, "业务接口异常"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(8, "不支持http媒体类型"),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(9, "Http消息不可读异常"),
    SENTINEL_FLOW_EXCEPTION(91, "流控规则被触发"),
    SENTINEL_DEGRADE_EXCEPTION(92, "熔断规则被触发"),
    SENTINEL_PARAM_FLOW_EXCEPTION(93, "热点规则被触发"),
    SENTINEL_SYSTEM_BLOCK_EXCEPTION(94, "系统规则被触发"),
    SENTINEL_AUTHORITY_EXCEPTION(95, "授权规则被触发");

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
