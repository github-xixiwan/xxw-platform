package com.xxw.platform.module.web.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxw.platform.module.common.rest.Result;
import com.xxw.platform.module.web.constant.CustomBusinessError;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(998)
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException ex) throws Exception {
        Result<Object> failure = Result.failure(CustomBusinessError.UNKNOWN_EXCEPTION);
        if (ex instanceof FlowException) {
            failure = Result.failure(CustomBusinessError.SENTINEL_FLOW_EXCEPTION);
        } else if (ex instanceof DegradeException) {
            failure = Result.failure(CustomBusinessError.SENTINEL_DEGRADE_EXCEPTION);
        } else if (ex instanceof ParamFlowException) {
            failure = Result.failure(CustomBusinessError.SENTINEL_PARAM_FLOW_EXCEPTION);
        } else if (ex instanceof SystemBlockException) {
            failure = Result.failure(CustomBusinessError.SENTINEL_SYSTEM_BLOCK_EXCEPTION);
        } else if (ex instanceof AuthorityException) {
            failure = Result.failure(CustomBusinessError.SENTINEL_AUTHORITY_EXCEPTION);
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), failure);
    }
}
