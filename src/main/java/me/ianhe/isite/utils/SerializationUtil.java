package me.ianhe.isite.utils;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * 对象序列化工具
 * 要序列化的类需实现Serializable接口
 *
 * @author iHelin
 * @create 2017-04-12 22:53
 */
public class SerializationUtil {

    public static byte[] write(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    public static <T> T read(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }

    private SerializationUtil() {
        //工具类不允许实例化
    }

}
