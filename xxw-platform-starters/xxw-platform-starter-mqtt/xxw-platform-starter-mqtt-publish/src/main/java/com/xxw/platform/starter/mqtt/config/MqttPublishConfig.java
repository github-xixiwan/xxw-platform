package com.xxw.platform.starter.mqtt.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(MqttPublishProperties.class)
public class MqttPublishConfig {

    /**
     * 出站消息BEAN
     */
    public static final String MQTT_OUTBOUND_CHANNEL = "mqttOutboundChannel";

    @Resource
    private MqttPublishProperties mqttPublishProperties;

    /**
     * MQTT配置属性（发布者）
     *
     * @return
     */
    private MqttConnectOptions getPublishMqttConnectOptions() {
        Assert.state(ArrayUtils.isNotEmpty(mqttPublishProperties.getPublish().getUris()), "publish uris cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttPublishProperties.getPublish().getUserName()), "publish userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttPublishProperties.getPublish().getPassword()), "publish password cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttPublishProperties.getPublish().getClientId()), "publish clientId cannot be empty");
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户
        options.setUserName(mqttPublishProperties.getPublish().getUserName());
        // 设置连接的密码
        options.setPassword(mqttPublishProperties.getPublish().getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttPublishProperties.getPublish().getUris());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttPublishProperties.getPublish().getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttPublishProperties.getPublish().getKeepAliveInterval());
        //每秒最大发送次数
        options.setMaxInflight(mqttPublishProperties.getPublish().getMaxInflight());
        // 设置遗嘱消息,并保留
        byte[] payload = ("[" + mqttPublishProperties.getPublish().getClientId() + "] 连接已断开...").getBytes(StandardCharsets.UTF_8);
        options.setWill("willTopic", payload, 1, true);
        return options;
    }

    /**
     * 出站消息
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = MQTT_OUTBOUND_CHANNEL)
    public MessageHandler mqttOutbound() {
        Assert.state(StringUtils.isNotBlank(mqttPublishProperties.getPublish().getClientId()), "publish clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttPublishProperties.getPublish().getDefaultTopic()), "publish defaultTopic cannot be empty");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getPublishMqttConnectOptions());
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttPublishProperties.getPublish().getClientId(), factory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttPublishProperties.getPublish().getDefaultTopic());
        return messageHandler;
    }

    /**
     * 出站消息渠道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}