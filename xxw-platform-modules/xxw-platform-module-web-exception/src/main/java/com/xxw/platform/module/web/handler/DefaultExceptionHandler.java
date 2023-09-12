package com.xxw.platform.module.web.handler;

import com.xxw.platform.module.common.exception.BusinessException;
import com.xxw.platform.module.common.exception.ElasticsearchException;
import com.xxw.platform.module.common.exception.SystemException;
import com.xxw.platform.module.common.rest.Result;
import com.xxw.platform.module.web.constant.CustomBusinessError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Order(999)
@Slf4j(topic = "exLog")
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception ex) {
        return internalHandlerException(ex);
    }

    private Result<Void> internalHandlerException(Exception ex) {
        if (ex instanceof BusinessException) {
            return handelException((BusinessException) ex);
        } else if (ex instanceof ElasticsearchException) {
            return handelException((ElasticsearchException) ex);
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
        log.error("Exception:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.UNKNOWN_EXCEPTION);
    }

    private Result<Void> handelException(BusinessException ex) {
        log.warn("BusinessException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.BUSINESS_EXCEPTION);
    }

    private Result<Void> handelException(ElasticsearchException ex) {
        log.warn("ElasticsearchException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.ELASTICSEARCH_EXCEPTION);
    }

    private Result<Void> handelException(SystemException ex) {
        log.warn("SystemException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.SYSTEM_EXCEPTION);
    }

    private Result<Void> handelException(RestClientException ex) {
        log.warn("RestClientException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.REST_CLIENT_EXCEPTION);
    }

    private Result<Void> handelException(NoHandlerFoundException ex) {
        log.warn("NoHandlerFoundException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.NO_HANDLER_FOUND_EXCEPTION);
    }

    private Result<Void> handelException(MissingServletRequestParameterException ex) {
        log.warn("MissingServletRequestParameterException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
    }

    private Result<Void> handelException(HttpMediaTypeNotSupportedException ex) {
        log.warn("HttpMediaTypeNotSupportedException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION);
    }

    private Result<Void> handelException(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException:{}", ExceptionUtils.getStackTrace(ex));
        return Result.failure(CustomBusinessError.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
    }
}
