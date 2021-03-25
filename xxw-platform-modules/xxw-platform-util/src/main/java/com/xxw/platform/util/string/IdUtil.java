package com.xxw.platform.util.string;

import java.util.UUID;

/**
 * 生成id
 *
 * @author wangyidan
 * @since 2019-12-02
 */
public final class IdUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-",""); // todo
    }
}
