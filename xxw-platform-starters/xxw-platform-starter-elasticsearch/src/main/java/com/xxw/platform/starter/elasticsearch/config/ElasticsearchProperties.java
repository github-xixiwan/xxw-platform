package com.xxw.platform.starter.elasticsearch.config;

import lombok.Data;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.es")
public class ElasticsearchProperties {

    /**
     * host:port 多个用,隔开
     */
    private String hostAddress;

    /**
     * 协议
     */
    private String schema;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 建立连接超时时间 默认1000ms
     */
    private int connectTimeout = RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS;

    /**
     * 数据传输超时时间 默认30000ms
     */
    private int socketTimeout = RestClientBuilder.DEFAULT_SOCKET_TIMEOUT_MILLIS;

    /**
     * 最大路由连接数 默认10
     */
    private int maxConnPerRoute = RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE;

    /**
     * 最大连接数 默认30
     */
    private int maxConnTotal = RestClientBuilder.DEFAULT_MAX_CONN_TOTAL;
    ;
}