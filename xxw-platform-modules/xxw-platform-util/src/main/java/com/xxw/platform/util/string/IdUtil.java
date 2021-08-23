package com.xxw.platform.util.string;

import java.util.UUID;

public final class IdUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getTraceId() {
        Long id = UUID.randomUUID().getMostSignificantBits();
        if (id < 0L) {
            id = -id;
        }
        return String.valueOf(id);
    }
}
