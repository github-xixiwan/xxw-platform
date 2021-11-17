package com.xxw.platform.module.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xxw.platform.module.util.convert.NameConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class JsonUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapperSnakeCase = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(module);
        objectMapperSnakeCase.registerModule(module);

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        objectMapperSnakeCase.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapperSnakeCase.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapperSnakeCase.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    /**
     * 驼峰下划线互转
     * @param object 类的实例
     * @return JSON字符串
     */
    public static String toJsonSnakeCase(Object object) {
        try {
            return objectMapperSnakeCase.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("String to json faild : " + e.getMessage());
        }
        return null;
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("String to json faild : " + e.getMessage());
        }
        return null;

    }

    /**
     * @param <T>   泛型声明
     * @param json  JSON字符串
     * @param clazz 要转换对象的类型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }

        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("json to object failed : " + e.getMessage() + ", json String is : " + json);
        }
        return t;
    }


    /**
     * @param <T>   泛型声明
     * @param json  JSONARRAY字符串
     * @param clazz 要转换对象的类型
     * @return
     */
    public static <T> List<T> fromJsonAsArray(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }

        List<T> result = new ArrayList<>();

        JavaType javaType = getCollectionType(result.getClass(), clazz);
        try {
            JsonNode node = objectMapper.readTree(json);
            if (node.isArray()) {
                result = objectMapper.readValue(json, javaType);
            }
        } catch (IOException e) {
            logger.error("json to object failed : " + e.getMessage() + ", json String is : " + json);
        }
        return result;
    }

    /**
     * @param json JSON字符串
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }

        T t = null;
        try {
            t = objectMapper.readValue(json, type);
        } catch (IOException e) {
            //throw new UtilException(e.getMessage());
            logger.error("json to object failed : " + e.getMessage() + ", json String is : " + json);
        }
        return t;
    }

    /**
     * @param <T>   泛型声明
     * @param json  JSONARRAY字符串
     * @param type  要转换对象的类型
     * @return
     */
    public static <T> List<T> fromJsonAsArray(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }

        List<T> result = new ArrayList<>();

        JavaType javaType = getCollectionType(result.getClass(), type);
        try {
            JsonNode node = objectMapper.readTree(json);
            if (node.isArray()) {
                result = objectMapper.readValue(json, javaType);
            }
        } catch (IOException e) {
            logger.error("json to object failed : " + e.getMessage());
        }
        return result;
    }

    /**
     * @param <T>   泛型声明
     * @param json  JSON字符串
     * @param clazz 要转换对象的类型
     * @return
     */
    public static <T> T fromJsonSnakeCase(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }

        T t = null;
        try {
            t = objectMapperSnakeCase.readValue(json, clazz);
        } catch (IOException e) {
            //throw new UtilException(e.getMessage());
            logger.error("json to object failed : " + e.getMessage());
        }
        return t;
    }

    public static <T> T fromJsonSnakeCase(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        }

        T t = null;
        try {
            t = objectMapperSnakeCase.readValue(json, type);
        } catch (IOException e) {
            //throw new UtilException(e.getMessage());
            logger.warn("json to object failed : " + e.getMessage());
        }
        return t;
    }

    /**
     * 判断字符串是否为json
     *
     * @param json
     * @return
     */
    public static Boolean isGoodJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 递归处理json对象，使其索引调整为下划线命名方式
     *
     * @param jsonNode
     * @return
     */
    public static JsonNode recursiveCamel2Snake(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
            jsonNode.fields().forEachRemaining(entry -> objectNode.set(NameConvert.camelToUnderline(entry.getKey()), entry.getValue()));
            jsonNode = objectNode;
        }
        if (jsonNode.isArray()) {
            ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
            jsonNode.iterator().forEachRemaining(node -> arrayNode.add(recursiveCamel2Snake(node)));
            jsonNode = arrayNode;
        }
        return jsonNode;
    }

    /**
     * 递归处理json map对象，使其索引调整为下划线命名方式
     *
     * @param jsonNode
     * @return
     */
    public static Object recursiveCamel2Snake(Object jsonNode) {
        if (jsonNode instanceof Map) {
            Map<String, Object> map = new HashMap<>(2);
            ((Map<String, Object>)jsonNode).forEach((k, v) -> map.put(NameConvert.camelToUnderline(k), v));
            jsonNode = map;
        }
        if (jsonNode instanceof List) {
            List<Object> list = new ArrayList<>();
            ((List<Object>)jsonNode).forEach(object -> list.add(recursiveCamel2Snake(object)));
            jsonNode = list;
        }
        return jsonNode;
    }

    /**
     * 递归处理json对象，使其索引调整为驼峰命名方式
     *
     * @param jsonNode
     * @return
     */
    public static JsonNode recursiveSnake2Camel(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
            jsonNode.fields().forEachRemaining(entry -> objectNode.set(NameConvert.underlineToCamel(entry.getKey()), entry.getValue()));
            jsonNode = objectNode;
        }
        if (jsonNode.isArray()) {
            ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
            jsonNode.iterator().forEachRemaining(node -> arrayNode.add(recursiveCamel2Snake(node)));
            jsonNode = arrayNode;
        }
        return jsonNode;
    }

    /**
     * 递归处理json map对象，使其索引调整为驼峰命名方式
     *
     * @param jsonNode
     * @return
     */
    public static Object recursiveSnake2Camel(Object jsonNode) {
        if (jsonNode instanceof Map) {
            Map<String, Object> map = new HashMap<>(2);
            ((Map<String, Object>)jsonNode).forEach((k, v) -> map.put(NameConvert.underlineToCamel(k), v));
            jsonNode = map;
        }
        if (jsonNode instanceof List) {
            List<Object> list = new ArrayList<>();
            ((List<Object>)jsonNode).forEach(object -> list.add(recursiveSnake2Camel(object)));
            jsonNode = list;
        }
        return jsonNode;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    private static JavaType getCollectionType(Class<?> collectionClass, TypeReference<?> typeReference) {
        JavaType javaType = objectMapper.getTypeFactory().constructType(typeReference);
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, javaType);
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        @Override
        public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(dateFormatter.format(value));
        }
    }

    static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String json = p.getText();
            return LocalDate.parse(json, dateFormatter);
        }
    }

    static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        @Override
        public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(timeFormatter.format(value));
        }
    }

    static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String json = p.getText();
            return LocalTime.parse(json, timeFormatter);
        }
    }

    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(dateTimeFormatter.format(value));
        }
    }

    static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String json = p.getText();
            return LocalDateTime.parse(json, dateTimeFormatter);
        }
    }

}
