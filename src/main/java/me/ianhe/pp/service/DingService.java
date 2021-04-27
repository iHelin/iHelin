package me.ianhe.pp.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.ianhe.pp.config.SystemProperties;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author iHelin
 * @since 2017/11/14 17:04
 */
@Service
public class DingService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemProperties systemProperties;

    /**
     * 发送FeedCard消息
     *
     * @author iHelin
     * @since 2017/12/1 15:14
     */
    public void sendFeedCardMsg(JSONObject feedCard) {
        JSONObject sendData = new JSONObject();
        sendData.set("msgtype", "feedCard");
        sendData.set("feedCard", feedCard);
        doSend(sendData);
    }

    /**
     * 发送文本消息
     *
     * @author iHelin
     * @since 2017/5/17 11:16
     */
    public String sendTextMsg(String content) {
        JSONObject body = new JSONObject();
        body.set("msgtype", "text");
        JSONObject contentMap = new JSONObject();
        contentMap.set("content", content);
        body.set("text", contentMap);
        return doSend(body);
    }

    public String doSend(JSONObject data) {
        String token = systemProperties.getDingToken();
        Long timestamp = System.currentTimeMillis();
        String sign = sign(timestamp);

        String result = HttpRequest.post(systemProperties.getDingUrl()
                + "?access_token=" + token
                + "&timestamp=" + timestamp
                + "&sign=" + sign)
//            .form("access_token", token)
//                .form("sign", sign)
//                .form("timestamp", timestamp)
                .body(JSONUtil.toJsonStr(data))
                .execute().body();
        logger.info("Robot return {}", result);
        return result;
    }

    private String sign(Long timestamp) {
        String stringToSign = timestamp + "\n" + systemProperties.getDingSecret();
        byte[] bytesToSign = stringToSign.getBytes(StandardCharsets.UTF_8);
        String sign = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(systemProperties.getDingSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(bytesToSign);
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (Exception e) {
            logger.error("签名失败", e);
        }
        return sign;
    }

}
