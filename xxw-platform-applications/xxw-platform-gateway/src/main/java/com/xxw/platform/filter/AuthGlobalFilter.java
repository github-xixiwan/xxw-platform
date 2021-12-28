package com.xxw.platform.filter;

import com.xxw.platform.module.util.json.JsonUtil;
import com.xxw.platform.module.util.rest.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Order(-1)
@RefreshScope
@Component
public class AuthGlobalFilter implements GlobalFilter {

    @Value("${whiteList:}")
    private String whiteList;

    private boolean isWhiteList(String path) {
        System.out.println(path);
        System.out.println(whiteList);
        if (StringUtils.isNotBlank(whiteList)) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            String[] split = whiteList.split(",");
            for (String s : split) {
                if (antPathMatcher.match(s, path)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        boolean flag = isWhiteList(path);
        if (!flag) {
            ServerHttpResponse response = exchange.getResponse();
            Result<Object> failure = Result.failure(0, "需要鉴权");
            byte[] bytes = JsonUtil.toJson(failure).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            // 指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            // 输出http响应
            return response.writeWith(Mono.just(buffer));
        }
        // 放行: 使用过滤器链，将请求继续向下传递
        return chain.filter(exchange);
    }
}
