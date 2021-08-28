package com.xxw.platform.starter.redisson.lock.idempotent;

public class IdempotentException extends RuntimeException {

    public IdempotentException(String message) {
        super(message);
    }

}