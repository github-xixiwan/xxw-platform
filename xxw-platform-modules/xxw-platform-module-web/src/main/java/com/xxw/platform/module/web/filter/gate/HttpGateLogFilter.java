package com.xxw.platform.module.web.filter.gate;

import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.string.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Order(998)
@Component
public class HttpGateLogFilter extends OncePerRequestFilter {

    private static final Logger gateLog = LoggerFactory.getLogger("gateLog");

    @Value("${spring.application.name}")
    private String sys;
    @Value("${server.port}")
    private String sysCode;

    private List<MediaType> mediaTypeList;

    public HttpGateLogFilter() {
        this.mediaTypeList = Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN, MediaType.TEXT_XML);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestToUse = request;
        boolean contentCache = false;
        if (this.needLogBody(request)) {
            contentCache = true;
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        HttpGateLog requestLog = HttpGateLog.createHttpGateLog((HttpServletRequest) requestToUse, this.sysCode, this.sys);
        if (StringUtils.isEmpty(request.getHeader("x-request-id"))) {
            MDC.put("traceId", IdUtil.getTraceId());
        } else {
            MDC.put("traceId", request.getHeader("x-request-id"));
        }

        requestLog.setTraceId(MDC.get("traceId"));
        Map<String, String> headers = extractHeaderToMap(request);
        headers.remove("x-original-uri");
        requestLog.setHeaders(headers);

        long startTime = System.currentTimeMillis();
        boolean var28 = false;

        try {
            var28 = true;
            filterChain.doFilter((ServletRequest) requestToUse, response);
            var28 = false;
        } finally {
            if (var28) {
                try {
                    if (!this.isAsyncStarted((HttpServletRequest) requestToUse)) {
                        Object requestBody = null;
                        if (HttpMethod.POST.toString().equals(request.getMethod()) && ("application/json".equalsIgnoreCase(request.getHeader("Content-Type")) || "application/json;charset=UTF-8".equalsIgnoreCase(request.getHeader("Content-Type")))) {
                            try {
                                ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) WebUtils.getNativeRequest((ServletRequest) requestToUse, ContentCachingRequestWrapper.class);
                                if (contentCache && wrapper != null) {
                                    byte[] buf = wrapper.getContentAsByteArray();
                                    if (buf.length > 0) {
                                        requestBody = new String(buf);
                                    }
                                }
                            } catch (Exception var29) {
                                gateLog.warn(ExceptionUtils.getStackTrace(var29));
                            }
                        }

                        if (requestBody != null) {
                            requestLog.setRequestBody(requestBody);
                        } else {
                            Map<String, String> reqParam = new HashMap();
                            Map<String, String[]> map = request.getParameterMap();
                            Set<String> key = map.keySet();
                            Iterator iterator = key.iterator();

                            while (iterator.hasNext()) {
                                String k = (String) iterator.next();
                                reqParam.put(k, ((String[]) map.get(k))[0]);
                            }

                            requestLog.setRequestBody(reqParam);
                        }

                        requestLog.setRespTime(LocalDateTime.now());
                        requestLog.setRetData(((HttpServletRequest) requestToUse).getAttribute("responseData"));
                        long executeTime = System.currentTimeMillis() - startTime;
                        gateLog.info("request-response-params: {},{}毫秒", JsonUtil.toJson(requestLog), executeTime);
                    }
                } catch (Exception var31) {
                    gateLog.warn(ExceptionUtils.getStackTrace(var31));
                }

            }
        }

        try {
            if (!this.isAsyncStarted((HttpServletRequest) requestToUse)) {
                Object requestBody = null;
                if (HttpMethod.POST.toString().equals(request.getMethod()) && ("application/json".equalsIgnoreCase(request.getHeader("Content-Type")) || "application/json;charset=UTF-8".equalsIgnoreCase(request.getHeader("Content-Type")))) {
                    try {
                        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) WebUtils.getNativeRequest((ServletRequest) requestToUse, ContentCachingRequestWrapper.class);
                        if (contentCache && wrapper != null) {
                            byte[] buf = wrapper.getContentAsByteArray();
                            if (buf.length > 0) {
                                requestBody = new String(buf);
                            }
                        }
                    } catch (Exception var30) {
                        gateLog.warn(ExceptionUtils.getStackTrace(var30));
                    }
                }

                if (requestBody != null) {
                    requestLog.setRequestBody(requestBody);
                } else {
                    Map<String, String> reqParam = new HashMap();
                    Map<String, String[]> map = request.getParameterMap();
                    Set<String> key = map.keySet();
                    Iterator iterator = key.iterator();

                    while (iterator.hasNext()) {
                        String k = (String) iterator.next();
                        reqParam.put(k, ((String[]) map.get(k))[0]);
                    }

                    requestLog.setRequestBody(reqParam);
                }

                requestLog.setRespTime(LocalDateTime.now());
                requestLog.setRetData(((HttpServletRequest) requestToUse).getAttribute("responseData"));
                long executeTime = System.currentTimeMillis() - startTime;
                gateLog.info("request-response-params: {},{}毫秒", JsonUtil.toJson(requestLog), executeTime);
            }
        } catch (Exception var33) {
            gateLog.warn(ExceptionUtils.getStackTrace(var33));
        }
    }

    private boolean needLogBody(HttpServletRequest request) {
        boolean isFirstRequest = !this.isAsyncDispatch(request);
        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            if (!"PUT".equalsIgnoreCase(request.getMethod()) && !"POST".equalsIgnoreCase(request.getMethod()) && !"DELETE".equalsIgnoreCase(request.getMethod())) {
                return false;
            } else {
                String contentType = request.getContentType();
                if (StringUtils.isNotBlank(contentType) && !contentType.trim().equalsIgnoreCase("null")) {
                    MediaType mediaType = MediaType.parseMediaType(contentType);
                    Stream var10000 = this.mediaTypeList.stream();
                    if (var10000.noneMatch(l -> l.equals(mediaType))) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    private static Map<String, String> extractHeaderToMap(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return null;
        } else {
            HashMap headerMap = new HashMap();
            while (headerNames.hasMoreElements()) {
                String header = (String) headerNames.nextElement();
                headerMap.put(header, request.getHeader(header));
            }
            return headerMap;
        }
    }
}
