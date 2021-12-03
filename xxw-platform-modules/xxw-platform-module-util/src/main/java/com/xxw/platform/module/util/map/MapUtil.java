package com.xxw.platform.module.util.map;

import java.util.Map;

/**
 * map工具类
 *
 * @author xxw
 * @since 2019/11/18
 */
public class MapUtil {

    /**
     * 判断map为空
     *
     * @param param 待检查参数
     * @return 为空，则返回true
     */
    public static boolean isEmpty(Map param) {
        return !(param != null && param.size() > 0);
    }

    /**
     * 判断map不能为空
     *
     * @param param 待检查参数
     * @return 不为空，则返回true
     */
    public static boolean isNotEmpty(Map param) {
        return (param != null && param.size() > 0);
    }

}
