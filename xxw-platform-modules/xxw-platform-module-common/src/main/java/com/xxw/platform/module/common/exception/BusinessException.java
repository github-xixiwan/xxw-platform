package com.xxw.platform.module.common.exception;

/**
 * 业务层面的错误基类,可直接使用
 *
 * @author xxw
 * @since 2019/11/18
 */
public class BusinessException extends BaseException {

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    public BusinessException(ErrorEnumInterface error, Object... args) {
        super(error, getErrorMessage(error.getMessage(), args));
    }

    public static void throwException(ErrorEnumInterface error,
                                      Object... args) throws BusinessException {
        throw getException(error, args);
    }

    public static BusinessException getException(ErrorEnumInterface error,
                                                 Object... args) {
        return new BusinessException(error, args);
    }

}
