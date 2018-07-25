package me.ianhe.isite.controller;

import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author iHelin
 * @since 2018/7/21 19:23
 */
@RestController
public class WeChatController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemProperties systemProperties;

    @GetMapping("/wxLogin")
    public void login(HttpServletResponse response) throws IOException {
        String backUrl = "http://" + systemProperties.getDomain() + "/callback";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        url = String.format(url, systemProperties.getWxAppid(), URLEncoder.encode(backUrl, "UTF-8"));
        logger.debug(url);
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<Object> callback(String code) throws JSONException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, systemProperties.getWxAppid(), systemProperties.getWxAppSecret(), code);
        String res = WeChatUtil.doGetStr(url);
        JSONObject resObj = new JSONObject(res);
        String openid = resObj.getString("openid");
        logger.debug("openid:{}", openid);
        String accessToken = resObj.getString("access_token");
        logger.debug("accessToken:{}", accessToken);
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s&openid=%s&lang=zh_CN";
        infoUrl = String.format(infoUrl, accessToken, openid);
        String infoStr = WeChatUtil.doGetStr(infoUrl);
        return new ResponseEntity<>(infoStr, HttpStatus.OK);
    }
}
