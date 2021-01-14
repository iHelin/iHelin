package me.ianhe.isite.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * JsonUtil
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * 基本对象转JSON String
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            LOGGER.error("encode(Object)", e);
            return null;
        }
    }

    /**
     * json反序列化
     *
     * @param json  字符串
     * @param clazz clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("decode(String, Class<T>)", e);
            return null;
        }
    }

    public static List<HashMap> parseArrayMap(String json) {
        TypeReference<List<HashMap>> jsonTypeReference = new TypeReference<List<HashMap>>() {
        };
        return parseArray(json, jsonTypeReference);
    }

    public static HashMap parseMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, HashMap.class);
        } catch (IOException e) {
            LOGGER.error("解析map异常,json:" + json, e);
            return null;
        }
    }

    public static HashMap parseMap(InputStream inputStream) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, HashMap.class);
        } catch (IOException e) {
            LOGGER.error("解析inputStream异常", e);
            return null;
        }
    }

    private static <T> T parseArray(String json, TypeReference<T> jsonTypeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, jsonTypeReference);
        } catch (IOException e) {
            LOGGER.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }

    private JsonUtil() {
        throw new UnsupportedOperationException("工具类不允许实例化");
    }
}
