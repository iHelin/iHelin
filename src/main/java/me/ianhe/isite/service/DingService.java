package me.ianhe.isite.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.pojo.ding.FeedCard;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
    public void sendFeedCardMsg(FeedCard feedCard) {
        Map<String, Object> sendData = Maps.newHashMap();
        sendData.put("msgtype", "feedCard");
        sendData.put("feedCard", JSONUtil.toJsonStr(feedCard));
        doSend(JSONUtil.toJsonStr(sendData));
    }

    /**
     * 发送文本消息
     *
     * @author iHelin
     * @since 2017/5/17 11:16
     */
    public String sendTextMsg(String content) {
        Map<String, Object> body = new HashMap<>();
        body.put("msgtype", "text");
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("content", content);
        body.put("text", contentMap);
        return doSend(JSONUtil.toJsonStr(body));
    }

    public String doSend(String data) {
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
                .body(data)
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
