package com.xxw.platform.starter.mqtt.config;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
     * MQTT配置属性（订阅者）
     *
     * @return
     */
    @Bean
    public MqttConnectOptions getSubscribeMqttConnectOptions() {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getSubscribe().getHostAddress()), "hostAddress cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getSubscribe().getUserName()), "userName cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getSubscribe().getPassword()), "password cannot be empty");
        MqttConnectOptions options = new MqttConnectOptions();

        options.setCleanSession(mqttProperties.getSubscribe().isCleanSession());
        // 设置连接的用户
        options.setUserName(mqttProperties.getSubscribe().getUserName());
        // 设置连接的密码
        options.setPassword(mqttProperties.getSubscribe().getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttProperties.getSubscribe().getHostAddress().split(","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttProperties.getSubscribe().getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        options.setKeepAliveInterval(mqttProperties.getSubscribe().getKeepAliveInterval());
        options.setAutomaticReconnect(mqttProperties.getSubscribe().isAutomaticReconnect());
        options.setMaxReconnectDelay(mqttProperties.getSubscribe().getMaxReconnectDelay());
        return options;
    }

    /**
     * true为非持久订阅
     * <p>
     * 方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
     * <p>
     * 就是这里的clientId，服务器用来区分用户的，不能重复,clientId不能和发布的clientId一样，否则会出现频繁断开连接和重连的问题
     * 不仅不能和发布的clientId一样，而且也不能和其他订阅的clientId一样，如果想要接收之前的离线数据，这就需要将client的 setCleanSession
     * 设置为false，这样服务器才能保留它的session，再次建立连接的时候，它就会继续使用这个session了。 这时此连接clientId 是不能更改的。
     * 但是其实还有一个问题，就是使用热部署的时候还是会出现频繁断开连接和重连的问题，可能是因为刚启动时的连接没断开，然后热部署的时候又进行了重连，重启一	  *   下就可以了
     *
     * @return
     * @throws MqttException
     */
    @Bean
    public MqttClient mqttClient() throws MqttException {
        Assert.state(StringUtils.isNotBlank(mqttProperties.getSubscribe().getHostAddress()), "hostAddress cannot be empty");
        Assert.state(StringUtils.isNotBlank(mqttProperties.getSubscribe().getClientId()), "clientId cannot be empty");
        // MemoryPersistence设置clientId的保存形式，默认为以内存保存
        MqttClient client = new MqttClient(mqttProperties.getSubscribe().getHostAddress(), mqttProperties.getSubscribe().getClientId(), new MemoryPersistence());
        //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
        if (client.isConnected()) {
            client.disconnect();
        }
        client.connect(getSubscribeMqttConnectOptions());
        return client;
    }
}