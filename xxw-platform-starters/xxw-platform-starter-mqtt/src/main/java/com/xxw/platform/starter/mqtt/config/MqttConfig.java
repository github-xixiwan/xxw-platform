package com.xxw.platform.starter.mqtt.config;

import com.xxw.platform.module.common.constants.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

    public static final String MQTT_OUTPUT_CHANNEL = "mqttOutputChannel";

    public static final String MQTT_INPUT_CHANNEL = "mqttInputChannel";

    @Resource
    private MqttProperties mqttProperties;

    private MqttConnectOptions getMqttConnectOptions() {
        Assert.state(ArrayUtils.isNotEmpty(mqttProperties.getUris()), "mqtt uris cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getUserName()), "mqtt userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getPassword()), "mqtt password cannot be empty");
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户
        options.setUserName(mqttProperties.getUserName());
        // 设置连接的密码
        options.setPassword(mqttProperties.getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttProperties.getUris());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        //每秒最大发送次数
        options.setMaxInflight(mqttProperties.getMaxInflight());
        options.setCleanSession(mqttProperties.isCleanSession());
        options.setAutomaticReconnect(mqttProperties.isAutomaticReconnect());
        options.setMaxReconnectDelay(mqttProperties.getMaxReconnectDelay());
        // 设置遗嘱消息,并保留
        byte[] payload = ("[" + mqttProperties.getClientId() + "] 连接已断开...").getBytes(StandardCharsets.UTF_8);
        options.setWill(Constants.CommonConstant.WILL_TOPIC, payload, 1, true);
        return options;
    }

    /**
     * 发送通道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = MQTT_OUTPUT_CHANNEL)
    public MessageHandler mqttOutbound() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getClientId()), "mqtt clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getDefaultTopic()), "mqtt defaultTopic cannot be empty");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getClientId() + "-publish",
                factory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
        return messageHandler;
    }

    /**
     * 接收通道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 错误通道
     *
     * @return
     */
    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter adapter() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getClientId()), "mqtt clientId cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getDefaultTopic()), "mqtt defaultTopic cannot be empty");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        String[] subscribeTopics = mqttProperties.getSubscribeTopics();
        MqttPahoMessageDrivenChannelAdapter adapter = null;
        if (ArrayUtils.isNotEmpty(subscribeTopics)) {
            adapter = new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + "-subscribe", factory,
                    subscribeTopics);
        } else {
            adapter = new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + "-subscribe", factory,
                    mqttProperties.getDefaultTopic());
        }
        adapter.setCompletionTimeout(mqttProperties.getCompletionTimeout());
        adapter.setRecoveryInterval(mqttProperties.getRecoveryInterval());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setErrorChannel(errorChannel());
        return adapter;
    }

    @Bean
    public MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
        return adapter;
    }
}