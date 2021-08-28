package com.xxw.platform.starter.redisson.lock.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 锁的名称，唯一性（默认为方法名）
     *
     * @return
     */
    String value() default "";

    /**
     * SPEL表达式，获取幂等Key，默认会从线程上下文中获取框架提供的幂等ID
     *
     * @return
     */
    String spelKey() default "";

    /**
     * 一级存储过期时间
     *
     * @return
     */
    int firstLevelExpireTime() default 10;

    /**
     * 二级存储过期时间
     *
     * @return
     */
    int secondLevelExpireTime() default 600;

    /**
     * 锁的过期时间
     *
     * @return
     */
    int lockExpireTime() default 10;

    /**
     * 存储时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}