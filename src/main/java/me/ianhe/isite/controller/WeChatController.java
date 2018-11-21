package me.ianhe.isite.controller;

import com.google.common.collect.Maps;
import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.utils.JsonUtil;
import me.ianhe.isite.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author iHelin
 * @since 2018/7/21 19:23
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemProperties systemProperties;

    /**
     * 小程序登录
     *
     * @param code
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<String> login(String code) {
        if (StringUtils.isEmpty(code)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + systemProperties.getXcxAppid() + "&secret=" + systemProperties.getXcxSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("url is : {}", url);
        String res = WeChatUtil.doGetStr(url);
        logger.info("res is :{}", res);
        Map<String, Object> resMap = JsonUtil.parseMap(res);
        String openid = (String) resMap.get("openid");
        if (StringUtils.hasText(openid)) {
            String sessionKey = (String) resMap.get("session_key");
            Integer expiresIn = (Integer) resMap.get("expires_in");
            Map<String, Object> loginMap = Maps.newHashMap();
            loginMap.put("openId", openid);
            loginMap.put("sessionKey", sessionKey);
            String redisLoginKey = "wechat:session:" + openid;
            commonRedisDao.setMapTimeout(redisLoginKey, loginMap, Long.valueOf(expiresIn), TimeUnit.SECONDS);
            return new ResponseEntity<>(redisLoginKey, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/wxLogin")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String backUrl = request.getScheme() + "://" + systemProperties.getDomain() + "/callback";
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
