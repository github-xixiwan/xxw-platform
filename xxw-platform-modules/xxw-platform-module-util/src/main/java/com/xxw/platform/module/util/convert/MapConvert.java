package com.xxw.platform.module.util.convert;

import com.xxw.platform.module.util.math.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * MAP转换为其他类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class MapConvert {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MapConvert.class);

    /**
     * map转换为bean对象
     *
     * @param source       map中的value的数据类型一定要和目标对象属性的数据类型一致
     * @param targetClass  目标对象
     */
    public static void map2Bean(Map<String, Object> source, Object targetClass) {
        try {
            BeanInfo beanInfo = null;
            beanInfo = Introspector.getBeanInfo(targetClass.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (source.containsKey(key)) {
                    Object value = source.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(targetClass, new Object[] {value});
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            //throw new UtilException("IllegalAccessException :  " + e.getMessage());
            logger.error("IllegalAccessException :  " + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            logger.error("InvocationTargetException :  " + e.getMessage());
            //throw new UtilException("InvocationTargetException :  " + e.getMessage());
        } catch (IntrospectionException e) {
            e.printStackTrace();
            logger.error("IntrospectionException :  " + e.getMessage());
            //throw new UtilException("IntrospectionException :  " + e.getMessage());
        }
    }

    /**
     * Map转换为bean对象
     * @param source  源对象
     * @param clazz   目标对象的类型
     * @param <T>     泛型
     * @return
     */
    public static <T> T map2Bean(Map<String, Object> source, Class<T> clazz) {
        try {
            Object o = clazz.newInstance();
            map2Bean(source, o);
            @SuppressWarnings("unchecked")
            T t = (T)o;
            return t;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            logger.error("IllegalAccessException |  InstantiationException:  " + e.getMessage());
            // throw new UtilException("IllegalAccessException |  InstantiationException:  " + e.getMessage());
        }
        return null;
    }


    /**
     * map转换为bean   只有属性名相同就会转换  不推荐  目前只支持long和integ
     * @param source  源对象
     * @param clazz   目标对象类型
     * @param <T>     泛型
     * @return
     */
    public static <T> T map2BeanForce(Map<String, String> source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (source.containsKey(key)) {
                    String value = source.get(key);
                    if(StringUtils.isNoneBlank(value)) {
                        // 得到property对应的setter方法
                        Method setter = property.getWriteMethod();
                        Type[] types = setter.getGenericParameterTypes();
                        setValue(result, setter, value, types[0]);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            logger.error("InstantiationException :  " + e.getMessage());
            //throw new UtilException("InstantiationException :  " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("InstantiationException :  " + e.getMessage());
            //throw new UtilException("IllegalAccessException :  " + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            logger.error("InvocationTargetException :  " + e.getMessage());
            //throw new UtilException("InvocationTargetException :  " + e.getMessage());
        } catch (IntrospectionException e) {
            e.printStackTrace();
            logger.error("IntrospectionException :  " + e.getMessage());
            //throw new UtilException("IntrospectionException :  " + e.getMessage());
        }

        return result;

    }

    /**
     * 设置值
     * 欢迎添加
     *
     * @param targetClass  目标实例
     * @param method       set方法
     * @param value        具体的数值
     * @param type         目标对象类型
     */
    private static void setValue(Object targetClass, Method method, String value, Type type)
            throws InvocationTargetException, IllegalAccessException {
        Object ov = value;
        switch (type.getTypeName()) {
            case "java.lang.Integer" : {
                ov = NumberUtil.toInteger(value);
                break;
            }
            case "java.lang.Long" : {
                ov = NumberUtil.toLong(value);
                break;
            }
            case "java.lang.Boolean" : {
                ov = Boolean.valueOf(value);
                break;
            }
            default : break;
        }
        method.invoke(targetClass, ov);
    }

}
