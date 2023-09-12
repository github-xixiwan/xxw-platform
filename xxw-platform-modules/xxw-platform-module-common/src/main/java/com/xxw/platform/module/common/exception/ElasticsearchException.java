package com.xxw.platform.module.common.exception;

public class ElasticsearchException extends BaseException {

    public ElasticsearchException(Integer code, String message) {
        super(code, message);
    }

    public ElasticsearchException(ErrorEnumInterface error, Object... args) {
        super(error, getErrorMessage(error.getMessage(), args));
    }

    public static void throwException(ErrorEnumInterface error, Object... args) throws ElasticsearchException {
        throw getException(error, args);
    }

    public static ElasticsearchException getException(ErrorEnumInterface error, Object... args) {
        return new ElasticsearchException(error, args);
    }


}
