package com.xxw.platform.module.web.handler;

import com.xxw.platform.module.util.exception.BusinessException;
import com.xxw.platform.module.util.exception.SystemException;
import com.xxw.platform.module.util.rest.Result;
import com.xxw.platform.module.web.constant.CustomBusinessError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Order(999)
@RestControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger exLog = LoggerFactory.getLogger("exLog");

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception ex) {
        return internalHandlerException(ex);
    }

    private Result<Void> internalHandlerException(Exception ex) {
        if (ex instanceof BusinessException) {
            return handelException((BusinessException) ex);
        } else if (ex instanceof SystemException) {
            return handelException((SystemException) ex);
        } else if (ex instanceof RestClientException) {
            return handelException((RestClientException) ex);
        } else if (ex instanceof NoHandlerFoundException) {
            return handelException((NoHandlerFoundException) ex);
        } else if (ex instanceof MissingServletRequestParameterException) {
            return handelException((MissingServletRequestParameterException) ex);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return handelException((HttpMediaTypeNotSupportedException) ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            return handelException((HttpMessageNotReadableException) ex);
        }
        return handelException(ex);
    }

    private Result<Void> handelException(Exception ex) {
        exLog.error("Exception:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.UNKNOWN_EXCEPTION);
    }

    private Result<Void> handelException(BusinessException ex) {
        exLog.warn("BusinessException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.BUSINESS_EXCEPTION);
    }

    private Result<Void> handelException(SystemException ex) {
        exLog.warn("SystemException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.SYSTEM_EXCEPTION);
    }

    private Result<Void> handelException(RestClientException ex) {
        exLog.warn("RestClientException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.REST_CLIENT_EXCEPTION);
    }

    private Result<Void> handelException(NoHandlerFoundException ex) {
        exLog.warn("NoHandlerFoundException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.NO_HANDLER_FOUND_EXCEPTION);
    }

    private Result<Void> handelException(MissingServletRequestParameterException ex) {
        exLog.warn("MissingServletRequestParameterException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
    }

    private Result<Void> handelException(HttpMediaTypeNotSupportedException ex) {
        exLog.warn("HttpMediaTypeNotSupportedException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION);
    }

    private Result<Void> handelException(HttpMessageNotReadableException ex) {
        exLog.warn("HttpMessageNotReadableException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.Http_Message_Not_Readable_Exception);
    }

}
