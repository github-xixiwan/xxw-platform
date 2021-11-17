package com.xxw.platform.module.util.exception;

/**
 * 错误枚举类定义
 *
 * @author ethan
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
