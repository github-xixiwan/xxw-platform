package com.xxw.platform.module.common.exception;

/**
 * UTIL这个包里面的抛错
 *
 * @author xxw
 * @since 2019/11/18
 */
public class UtilException extends BaseException {
    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(String message) {
        super(message);
    }
}