package com.xxw.platform.module.mqtt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MqttSendDTO", description = "MqttSendDTO")
public class MqttSendDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主题")
    private String topic;

    @ApiModelProperty(value = "对消息处理的几种机制 " +
            "0 表示的是订阅者没收到消息不会再次发送，消息会丢失 " +
            "1 表示的是会尝试重试，一直到接收到消息，但这种情况可能导致订阅者收到多次重复消息" +
            "2 多了一次去重的动作，确保订阅者收到的消息有一次")
    private int qos;

    @ApiModelProperty(value = "消息主体")
    private String payload;

    private int size;
}
