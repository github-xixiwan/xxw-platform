package com.xxw.platform.module.util.exception;

/**
 * 系统级错误,该错误不可最终进行抛出,不可对外展示
 *
 * @author xxw
 * @since 2019/11/18
 */
public class SystemException extends BaseException {
    public SystemException(Integer code, String message) {
        super(code, message);
    }

    public SystemException(ErrorEnumInterface error, Object... args) {
        super(error, getErrorMessage(error.getMessage(), args));
    }

    public static void throwException(ErrorEnumInterface error,
                                      Object... args) throws SystemException {
        throw getException(error, args);
    }

    public static SystemException getException(ErrorEnumInterface error,
                                               Object... args) {
        return new SystemException(error, args);
    }
}
