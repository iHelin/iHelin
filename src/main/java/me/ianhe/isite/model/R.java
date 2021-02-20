package me.ianhe.isite.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(R.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R putData(Object value) {
        super.put("data", value);
        return this;
    }


    public <T> T getData(TypeReference<T> typeReference) {
        Object data = get("data");
        T value = null;
        try {
            String valueAsString = OBJECT_MAPPER.writeValueAsString(data);
            value = OBJECT_MAPPER.readValue(valueAsString, typeReference);
        } catch (JsonProcessingException e) {
            LOGGER.error("getData error", e);
        }
        return value;
    }

    public boolean success() {
        return (Integer) super.get("code") == 0;
    }

    public int getCode() {
        return (int) get("code");
    }
}
