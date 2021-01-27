package me.ianhe.isite.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "seven")
public class SystemProperties {

    /**
     * 公众号
     */
    private String wxAppid;
    private String wxAppSecret;
    private String domain;

    /**
     * 钉钉
     */
    private String dingUrl;
    private String dingSign;
    private String dingToken;
    /**
     * 七牛存储
     */
    private String qnAccessKey;
    private String qnSecretKey;
    private String qnPrefix;
    private String qnBucket;
    /**
     * 小程序appid
     */
    private String xcxAppid;
    private String xcxSecret;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getWxAppSecret() {
        return wxAppSecret;
    }

    public void setWxAppSecret(String wxAppSecret) {
        this.wxAppSecret = wxAppSecret;
    }

    public String getDingUrl() {
        return dingUrl;
    }

    public void setDingUrl(String dingUrl) {
        this.dingUrl = dingUrl;
    }

    public String getDingSign() {
        return dingSign;
    }

    public void setDingSign(String dingSign) {
        this.dingSign = dingSign;
    }

    public String getDingToken() {
        return dingToken;
    }

    public void setDingToken(String dingToken) {
        this.dingToken = dingToken;
    }

    public String getQnAccessKey() {
        return qnAccessKey;
    }

    public void setQnAccessKey(String qnAccessKey) {
        this.qnAccessKey = qnAccessKey;
    }

    public String getQnSecretKey() {
        return qnSecretKey;
    }

    public void setQnSecretKey(String qnSecretKey) {
        this.qnSecretKey = qnSecretKey;
    }

    public String getQnPrefix() {
        return qnPrefix;
    }

    public void setQnPrefix(String qnPrefix) {
        this.qnPrefix = qnPrefix;
    }

    public String getQnBucket() {
        return qnBucket;
    }

    public void setQnBucket(String qnBucket) {
        this.qnBucket = qnBucket;
    }

    public String getXcxAppid() {
        return xcxAppid;
    }

    public void setXcxAppid(String xcxAppid) {
        this.xcxAppid = xcxAppid;
    }

    public String getXcxSecret() {
        return xcxSecret;
    }

    public void setXcxSecret(String xcxSecret) {
        this.xcxSecret = xcxSecret;
    }
}
