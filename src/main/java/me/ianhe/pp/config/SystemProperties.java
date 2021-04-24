package me.ianhe.pp.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system")
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
    private String dingToken;
    private String dingSecret;

    /**
     * jwt
     */
    private String jwtSecret;
    private Long jwtExpire;
    private String jwtHeader;

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

    public String getDingSecret() {
        return dingSecret;
    }

    public void setDingSecret(String dingSecret) {
        this.dingSecret = dingSecret;
    }

    public String getDingToken() {
        return dingToken;
    }

    public void setDingToken(String dingToken) {
        this.dingToken = dingToken;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public Long getJwtExpire() {
        return jwtExpire;
    }

    public void setJwtExpire(Long jwtExpire) {
        this.jwtExpire = jwtExpire;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }
}
