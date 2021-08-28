package com.xxw.platform.starter.redisson.lock.idempotent;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class IdempotentRequest {

    /**
     * 幂等Key
     */
    private String key;

    /**
     * 一级存储过期时间
     */
    private int firstLevelExpireTime;

    /**
     * 二级存储过期时间
     */
    private int secondLevelExpireTime;

    /**
     * 锁的过期时间
     */
    private int lockExpireTime;

    /**
     * 存储时间单位
     */
    private TimeUnit timeUnit;

}