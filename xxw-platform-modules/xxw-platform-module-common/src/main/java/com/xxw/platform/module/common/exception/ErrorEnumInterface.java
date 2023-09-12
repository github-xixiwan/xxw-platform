package com.xxw.platform.module.common.exception;

/**
 * 错误枚举类定义
 *
 * @author xxw
 * @since 2019/11/14
 */
public interface ErrorEnumInterface {

    /**
     * 获取code
     *
     * @return 错误code
     */
    Integer getCode();

    /**
     * 获取msg
     *
     * @return 错误msg
     */
    String getMessage();

}
