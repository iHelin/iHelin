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
                "?appid=" + systemProperties.getWxAppid() +
                "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8") +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE" +
                "#wechat_redirect";
        logger.debug(url);
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<Object> callback(String code) throws JSONException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + systemProperties.getWxAppid() +
                "&secret=" + systemProperties.getWxAppSecret() +
                "&code=" + code +
                "&grant_type=authorization_code";
        String res = WeChatUtil.doGetStr(url);
        JSONObject resObj = new JSONObject(res);
        String openid = resObj.getString("openid");
        logger.debug("openid:{}", openid);
        String accessToken = resObj.getString("access_token");
        logger.debug("accessToken:{}", accessToken);
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";
        String infoStr = WeChatUtil.doGetStr(infoUrl);
        return new ResponseEntity<>(infoStr, HttpStatus.OK);
    }
}
