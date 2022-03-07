package com.xxw.platform.starter.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttPublishProperties {

    /**
     * 发布者
     */
    private Publish publish;

    @Data
    public static class Publish {

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
         * 客户端唯一ID
         */
        private String clientId;

        /**
         * 每秒最大发送次数
         */
        private int maxInflight = 10;
    }
}