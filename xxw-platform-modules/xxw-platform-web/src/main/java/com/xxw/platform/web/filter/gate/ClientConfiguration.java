package com.xxw.platform.web.filter.gate;


import com.xxw.platform.util.string.IdUtil;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientConfiguration {

    public ClientConfiguration() {
    }

    @Bean
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                Map<String, Collection<String>> headers = new HashMap();
                HttpServletRequest request = attributes.getRequest();
                if (StringUtils.isNotBlank(request.getHeader("x-request-id"))) {
                    request.setAttribute("x-request-id", request.getHeader("x-request-id"));
                    headers.put("x-request-id", Arrays.asList(request.getHeader("x-request-id")));
                } else {
                    String traceId;
                    if (request.getAttribute("x-request-id") != null) {
                        traceId = request.getAttribute("x-request-id").toString();
                        headers.put("x-request-id", Arrays.asList(traceId));
                    } else {
                        traceId = IdUtil.getTraceId();
                        request.setAttribute("x-request-id", traceId);
                        headers.put("x-request-id", Arrays.asList(traceId));
                    }
                }

                template.headers(headers);
            }
        };
    }
}
