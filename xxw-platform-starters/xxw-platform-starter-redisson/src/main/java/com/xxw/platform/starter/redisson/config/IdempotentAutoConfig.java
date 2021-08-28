package com.xxw.platform.starter.redisson.config;

import com.xxw.platform.starter.redisson.lock.DistributedLock;
import com.xxw.platform.starter.redisson.lock.idempotent.DistributedIdempotent;
import com.xxw.platform.starter.redisson.lock.idempotent.DistributedIdempotentAspect;
import com.xxw.platform.starter.redisson.lock.idempotent.DistributedIdempotentImpl;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@AutoConfigureAfter(DistributedLockAutoConfig.class)
public class IdempotentAutoConfig {

    @Resource
    private DistributedIdempotent distributedIdempotent;

    @Bean
    public DistributedIdempotent distributedIdempotent(RedissonClient redissonClient, DistributedLock distributedLock) {
        return new DistributedIdempotentImpl(redissonClient, distributedLock);
    }

    @Bean
    public DistributedIdempotentAspect distributedIdempotentAspect() {
        return new DistributedIdempotentAspect(distributedIdempotent);
    }

}
