package com.xxw.platform.starter.redisson.config;

import com.xxw.platform.starter.redisson.lock.DistributedLock;
import com.xxw.platform.starter.redisson.lock.DistributedLockMysql;
import com.xxw.platform.starter.redisson.lock.DistributedLockRedis;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DistributedLockAutoConfig {

    @Resource
    private DataSource dataSource;

    @Bean("distributedLockMysql")
    public DistributedLock distributedLockMysql() {
        return new DistributedLockMysql(dataSource);
    }

    @Bean("distributedLockRedis")
    @Primary
    @DependsOn("distributedLockMysql")
    public DistributedLock distributedLockRedis(RedissonClient redissonClient, DistributedLockMysql distributedLockMysql) {
        return new DistributedLockRedis(redissonClient, distributedLockMysql);
    }
}
