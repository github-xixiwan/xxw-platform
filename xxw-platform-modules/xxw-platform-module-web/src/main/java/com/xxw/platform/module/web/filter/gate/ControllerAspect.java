package com.xxw.platform.module.web.filter.gate;

import com.xxw.platform.module.util.string.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class ControllerAspect {
    private final String controllerPoint = "execution(* com.yl..*.controller..*.*(..))";

    public ControllerAspect() {
    }

    @AfterReturning(
            returning = "response",
            pointcut = "execution(* com.xxw.platform..*.controller..*.*(..))"
    )
    public void doAfterReturning(Object response) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute("responseData", response);
        }
    }

    @Before("execution(* com.xxw.platform..*.controller..*.*(..))")
    public void beforeControoler() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (StringUtils.isNotBlank(request.getHeader("x-request-id"))) {
                request.setAttribute("x-request-id", request.getHeader("x-request-id"));
            } else {
                request.setAttribute("x-request-id", IdUtil.getTraceId());
            }

        }
    }
}
