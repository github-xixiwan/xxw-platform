package com.xxw.platform.starter.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttProperties {

    /**
     * tcp://host:port 多个用,隔开
     */
    private String[] uris;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 建立连接超时时间 默认30s
     */
    private int connectionTimeout = 30;

    /**
     * 心跳时间 默认60s
     */
    private int keepAliveInterval = 60;

    /**
     * 默认发送的主题
     */
    private String defaultTopic;

    /**
     * 订阅的主题
     */
    private String[] subscribeTopics;

    /**
     * 客户端唯一ID
     */
    private String clientId;

    /**
     * 每秒最大发送次数
     */
    private int maxInflight = 10;

    /**
     * 把配置里的 cleanSession 设为false，客户端掉线后 服务器端不会清除session，
     * 当重连后可以接收之前订阅主题的消息。当客户端上线后会接受到它离线的这段时间的消息，
     * 如果短线需要删除之前的消息则可以设置为true
     */
    private boolean cleanSession = true;

    /**
     * 返回如果连接丢失，客户端是否会自动尝试重新连接到服务器
     */
    private boolean automaticReconnect = false;

    /**
     * 获取重新连接之间的最长等待时间（毫秒）
     */
    private int maxReconnectDelay = 128000;

    /**
     * 操作完成超时（毫秒）
     */
    private long completionTimeout = 30000;

    /**
     * 重新连接尝试之间的等待时间（毫秒）
     */
    private int recoveryInterval = 10000;
}