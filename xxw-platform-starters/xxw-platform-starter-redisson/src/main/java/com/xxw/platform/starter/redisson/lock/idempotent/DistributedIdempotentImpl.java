package com.xxw.platform.starter.redisson.lock.idempotent;

import com.xxw.platform.starter.redisson.lock.DistributedLock;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class DistributedIdempotentImpl implements DistributedIdempotent {

    private DistributedLock distributedLock;

    private RedissonClient redissonClient;

    /**
     * 锁名称后缀，区分锁和幂等的Key
     */
    private String lockSuffix = "_lock";

    /**
     * 幂等Key对应的默认值
     */
    private String idempotentDefaultValue = "1";

    public DistributedIdempotentImpl(RedissonClient redissonClient, DistributedLock distributedLock) {
        this.redissonClient = redissonClient;
        this.distributedLock = distributedLock;
    }

    @Override
    public <T> T execute(String key, int lockExpireTime, int firstLevelExpireTime, int secondLevelExpireTime, TimeUnit timeUnit, Supplier<T> execute, Supplier<T> fail) {
        // todo: 二级存储待实现
        return distributedLock.lock(key + lockSuffix, lockExpireTime, timeUnit, () -> {
            RBucket<String> bucket = redissonClient.getBucket(key);
            if (bucket != null && bucket.get() != null) {
                return fail.get();
            }

            T executeResult = execute.get();

            bucket.set(idempotentDefaultValue, firstLevelExpireTime, timeUnit);
            return executeResult;
        }, fail);
    }

    @Override
    public <T> T execute(IdempotentRequest request, Supplier<T> execute, Supplier<T> fail) {
        return execute(request.getKey(), request.getLockExpireTime(), request.getFirstLevelExpireTime(), request.getSecondLevelExpireTime(), request.getTimeUnit(), execute, fail);
    }
}