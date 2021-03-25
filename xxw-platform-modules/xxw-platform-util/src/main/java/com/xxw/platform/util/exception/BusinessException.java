package com.xxw.platform.util.exception;

/**
 * 业务层面的错误基类,可直接使用
 *
 * @author ethan
 * @since 2019/11/18
 */
public class BusinessException extends BaseException  {

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    public BusinessException(ErrorEnumInterface error, Object... args) {
        super(error, BaseException.getErrorMessage(error.getMessage(), args));
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
