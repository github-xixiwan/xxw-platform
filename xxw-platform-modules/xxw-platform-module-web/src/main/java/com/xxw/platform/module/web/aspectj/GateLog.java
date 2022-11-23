package com.xxw.platform.module.web.aspectj;

import com.google.common.collect.Maps;
import com.xxw.platform.module.util.json.JsonUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j(topic = "gateLog")
public class GateLog {

    /**
     * 拦截所有controller包下的方法
     */
    @Pointcut("execution(public * com.xxw.platform.controller..*.*(..))")
    private void controllerMethod() {
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("controllerMethod()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        final Log l = Log.builder().threadId(Long.toString(Thread.currentThread().getId())).threadName(Thread.currentThread().getName()).ip(getIp(request)).url(request.getRequestURL().toString()).classMethod(String.format("%s.%s", point.getSignature().getDeclaringTypeName(), point.getSignature().getName())).httpMethod(request.getMethod()).requestParams(getNameAndValue(point)).result(result).timeCost(System.currentTimeMillis() - startTime).userAgent(header).browser(userAgent.getBrowser().toString()).os(userAgent.getOperatingSystem().toString()).build();
        log.info("request_and_response:{}", JsonUtil.toJson(l));
        return result;
    }

    /**
     * 获取方法参数名和参数值
     *
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(names) || ArrayUtils.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "10.66.70.183";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Log {
        // 线程id
        private String threadId;
        // 线程名称
        private String threadName;
        // ip
        private String ip;
        // url
        private String url;
        // http方法 GET POST PUT DELETE PATCH
        private String httpMethod;
        // 类方法
        private String classMethod;
        // 请求参数
        private Object requestParams;
        // 返回参数
        private Object result;
        // 接口耗时
        private Long timeCost;
        // 操作系统
        private String os;
        // 浏览器
        private String browser;
        // user-agent
        private String userAgent;
    }
}
