package me.ianhe.isite.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author iHelin
 * @since 2018/7/9 22:23
 */
public class CaptchaCode implements Serializable {

    private static final long serialVersionUID = 4767348691312054447L;
    private transient BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    /**
     * 构造函数
     *
     * @param image
     * @param code
     * @param expireIn 多少秒后过期
     */
    public CaptchaCode(BufferedImage image, String code, long expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
