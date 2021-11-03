package com.xxw.platform.starter.mqtt.config;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

    /**
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    /**
     * 客户端与服务器之间的连接意外中断，服务器将发布客户端的"遗嘱"消息
     */
    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

    @Resource
    private MqttProperties mqttProperties;

    @Bean
    public MqttConnectOptions getSenderMqttConnectOptions() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getHostAddress()), "hostAddress cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getUserName()), "userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPassword()), "password cannot be empty");

        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户
        options.setUserName(mqttProperties.getUserName());
        // 设置连接的密码
        options.setPassword(mqttProperties.getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttProperties.getHostAddress().split(","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        // 设置 "遗嘱" 消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的"遗嘱"消息。
        options.setWill("willTopic", WILL_DATA, 2, false);
        return options;
    }

    /**
     * MQTT客户端
     */
    @Bean
    public MqttPahoClientFactory senderMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getSenderMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（生产者）
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT消息处理器（生产者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getClientId()), "clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getDefaultTopic()), "defaultTopic cannot be empty");
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getClientId(), senderMqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
        return messageHandler;
    }
}