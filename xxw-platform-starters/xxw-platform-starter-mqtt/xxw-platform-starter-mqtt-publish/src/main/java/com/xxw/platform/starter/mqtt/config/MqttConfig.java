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

    /**
     * MQTT配置属性（发布者）
     *
     * @return
     */
    @Bean
    public MqttConnectOptions getPublishMqttConnectOptions() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPublish().getHostAddress()), "hostAddress cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPublish().getUserName()), "userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPublish().getPassword()), "password cannot be empty");
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户
        options.setUserName(mqttProperties.getPublish().getUserName());
        // 设置连接的密码
        options.setPassword(mqttProperties.getPublish().getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttProperties.getPublish().getHostAddress().split(","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttProperties.getPublish().getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttProperties.getPublish().getKeepAliveInterval());
        // 设置 "遗嘱" 消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的"遗嘱"消息。
        options.setWill("willTopic", WILL_DATA, 2, false);
        return options;
    }

    /**
     * MQTT客户端（发布者）
     */
    @Bean
    public MqttPahoClientFactory getPublishMqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getPublishMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（发布者）
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel publishMessageChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT消息处理器（发布者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPublish().getClientId()), "clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPublish().getDefaultTopic()), "defaultTopic cannot be empty");
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getPublish().getClientId(), getPublishMqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttProperties.getPublish().getDefaultTopic());
        return messageHandler;
    }
}