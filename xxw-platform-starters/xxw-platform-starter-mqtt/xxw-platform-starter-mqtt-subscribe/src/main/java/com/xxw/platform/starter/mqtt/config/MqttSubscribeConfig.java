package com.xxw.platform.starter.mqtt.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(MqttSubscribeProperties.class)
public class MqttSubscribeConfig {

    @Resource
    private MqttSubscribeProperties mqttSubscribeProperties;

    /**
     * MQTT配置属性（订阅者）
     *
     * @return
     */
    private MqttConnectOptions getSubscribeMqttConnectOptions() {
        Assert.state(ArrayUtils.isNotEmpty(mqttSubscribeProperties.getSubscribe().getUris()), "subscribe uris cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttSubscribeProperties.getSubscribe().getUserName()), "subscribe userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttSubscribeProperties.getSubscribe().getPassword()), "subscribe password cannot be empty");
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户
        options.setUserName(mqttSubscribeProperties.getSubscribe().getUserName());
        // 设置连接的密码
        options.setPassword(mqttSubscribeProperties.getSubscribe().getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttSubscribeProperties.getSubscribe().getUris());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttSubscribeProperties.getSubscribe().getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttSubscribeProperties.getSubscribe().getKeepAliveInterval());
        options.setCleanSession(mqttSubscribeProperties.getSubscribe().isCleanSession());
        options.setAutomaticReconnect(mqttSubscribeProperties.getSubscribe().isAutomaticReconnect());
        options.setMaxReconnectDelay(mqttSubscribeProperties.getSubscribe().getMaxReconnectDelay());
        return options;
    }

    @Bean
    public MqttPahoClientFactory subscribeMqttPahoClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getSubscribeMqttConnectOptions());
        return factory;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter subscribeMqttPahoMessageDrivenChannelAdapter(MqttPahoClientFactory factory) {
        Assert.state(StringUtils.isNotBlank(mqttSubscribeProperties.getSubscribe().getClientId()), "subscribe clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttSubscribeProperties.getSubscribe().getDefaultTopic()), "subscribe defaultTopic cannot be empty");
        return new MqttPahoMessageDrivenChannelAdapter(mqttSubscribeProperties.getSubscribe().getClientId(), factory, mqttSubscribeProperties.getSubscribe().getDefaultTopic());
    }

    @Bean
    public MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        adapter.setCompletionTimeout(mqttSubscribeProperties.getSubscribe().getCompletionTimeout());
        adapter.setRecoveryInterval(mqttSubscribeProperties.getSubscribe().getRecoveryInterval());
        adapter.setOutputChannel(mqttInboundChannel());
        adapter.setErrorChannel(errorChannel());
        adapter.setQos(0);
        return adapter;
    }

    @Bean
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }
}