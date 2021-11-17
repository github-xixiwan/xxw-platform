package com.xxw.platform.module.util.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用list工具类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class ListUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ListUtil.class);


    /**
     * 列表转换成逗号分割的字符串  只针对基础数据类型
     *
     * @param list 基础数据类型list
     * @return
     */
    public static String join(List list) {
        return join(list, ",");
    }

    /**
     * 以某个字符间隔拼装起来  只针对基础数据类型
     *
     * @param list 任意list对象
     * @param c    间隔字符
     * @return
     */
    public static String join(List list, String c) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object each : list) {
            if (first) {
                first = false;
            } else {
                sb.append(c);
            }
            sb.append(each);
        }
        return sb.toString();
    }

    /**
     * 检测是否为空列表
     *
     * @param list 参数值
     * @param <T>  泛型
     * @return true:空数组
     */
    public static <T> boolean isEmpty(List<T> list) {
        return !(list != null && list.size() > 0);
    }


    /**
     * 比如：List<UserDO> 转成 Map<UserId, UserDO>
     *
     * @param list          bean对象list
     * @param keyNameInList map的key取值的字段
     * @param <T>           泛型
     * @return map映射
     */
    public static <K, T> Map<K, T> listToMap(List<T> list, String keyNameInList) {
        Map<K, T> map = new HashMap<>(2);
        if (isEmpty(list)) {
            return map;
        }

        Class<?> clazz = list.get(0).getClass();
        Method method = getBeanMethod(clazz, keyNameInList, "get");
        if (method == null) {
            return map;
        }

        map = list.stream().collect(Collectors.toMap(t -> findKeyValue(t, method), t -> t));
        return map;
    }

    private static <K, T> K findKeyValue(T object, Method method) {
        try {
            K k = (K) method.invoke(object);
            return k;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error("list to map failed , cause : " + e.getMessage());
        }
        return null;
    }

    private static Method getBeanMethod(Class<?> clazz, String propertyName, String prefix) {
        char[] cs = propertyName.toCharArray();
        cs[0] -= 32;
        String methodName = prefix + String.valueOf(cs);
        try {
            return clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}
