package me.ianhe.isite.utils;

import me.ianhe.isite.exception.SystemException;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 * 验证
 *
 * @author iHelin
 * @since 2017/10/17 15:28
 */
public class CheckUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckUtil.class);

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        // sha1加密
        String temp = sha1(content.toString());
        return temp.equals(signature);
    }

    public static String sha1(String s) {
        return DigestUtils.sha1Hex(s);
    }

    public static String md5(byte[] content) {
        return DigestUtils.md5Hex(content);
    }

    public static String md5(String s) {
        return DigestUtils.md5Hex(s);
    }

    /**
     * md5文件加密
     *
     * @param f
     * @return
     */
    public static String md5(File f) {
        try (FileInputStream fis = new FileInputStream(f)) {
            byte[] bytes = DigestUtils.md5(fis);
            return new String(bytes);
        } catch (IOException e) {
            LOGGER.error("md5 error!", e);
            return null;
        }
    }

    public static String encodeBase64(String s) {
        byte[] bytes = Base64.getEncoder().encode(s.getBytes());
        return new String(bytes);
    }

    public static String decodeBase64(String s) {
        byte[] bytes = Base64.getDecoder().decode(s.getBytes());
        return new String(bytes);
    }

    private CheckUtil() {
        throw new SystemException("工具类不允许实例化！");
    }

}
