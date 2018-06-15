package me.ianhe.isite.utils;

import me.ianhe.isite.entity.User;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;

/**
 * @author iHelin
 * @since 2018/6/15 16:29
 */
public class SystemUtils {

    /**
     * 判断是否为number
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static byte[] write(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    public static <T> T read(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }

    private SystemUtils() {
        //工具类不允许实例化
    }
}
