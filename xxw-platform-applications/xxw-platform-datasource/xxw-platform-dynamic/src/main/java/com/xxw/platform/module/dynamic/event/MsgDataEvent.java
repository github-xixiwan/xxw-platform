package com.xxw.platform.module.dynamic.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgDataEvent {

    /**
     * 消息
     */
    private String msg;
}
