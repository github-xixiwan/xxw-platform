package com.xxw.platform.module.util.convert;

import com.xxw.platform.module.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * bean之间的转换类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class BeanConvert {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BeanConvert.class);


    /**
     * 对象转换为 map对象  此处转换为HashMap
     * @param source     源对象  属性都有get方法
     * @return           以属性名为key的map
     */
    public static Map<String, Object> bean2Map(Object source) {
        if (source == null) {
            return null;
        }

        try {
            Map<String, Object> map = new HashMap<String, Object>(16);
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(source);
                    if (null != value) {
                        map.put(key, value);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            logger.error("transBean2Map source: " +  JsonUtil.toJson(source) + ", Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * 转换为 TreeMap<String, String>
     * @param source 源对象  属性必须有get方法
     * @return
     */
    public static TreeMap<String, String> bean2TreeMap(Object source) {
        if (source == null) {
            return null;
        }
        TreeMap<String, String> map = new TreeMap<String, String>();
        bean2Map(source,map);
        return map;
    }


    /**
     * 转换为 HashMap<String, String>
     *
     * @param source 源对象  属性必须有get方法
     * @return
     */
    public static HashMap<String, String> bean2HashMap(Object source) {
        if (source == null) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        bean2Map(source,map);
        return map;
    }


    private static void  bean2Map(Object source,Map<String,String> map){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(source);
                    if (null != value) {
                        map.put(key, String.valueOf(value));
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("transBean2Map Error: " + e.getMessage());
        }
    }


}
