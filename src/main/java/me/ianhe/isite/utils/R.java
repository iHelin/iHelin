package me.ianhe.isite.utils;

import java.util.HashMap;

/**
 * 返回数据
 *
 * @author iHelin ihelin@outlook.com
 */
public class R extends HashMap<String, Object> {

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
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

    public boolean success() {
        return (Integer) super.get("code") == 0;
    }

    public int getCode() {
        return (int) get("code");
    }
}
