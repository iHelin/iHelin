package me.ianhe.isite.controller;

import com.google.common.collect.Maps;
import io.jsonwebtoken.ExpiredJwtException;
import me.ianhe.isite.config.SystemProperties;
import me.ianhe.isite.utils.JsonUtil;
import me.ianhe.isite.utils.JwtUtil;
import me.ianhe.isite.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

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
     * @param body
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String code = body.get("code");
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
        String sessionKey = (String) resMap.get("session_key");
        logger.debug("openid : {},sessionKey:{}", openid, sessionKey);
        if (StringUtils.hasText(openid)) {
            Map<String, Object> resultMap = Maps.newHashMap();
            String token = JwtUtil.createJWT(60L * 60 * 24 * 7, openid);
            resultMap.put("token", token);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            JwtUtil.parseJWT(token);
            logger.debug("auth success");
            resultMap.put("status", "success");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            logger.warn("token 过期", e);
            resultMap.put("status", "error");
            resultMap.put("msg", "token 已过期");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("token 不合法", e);
            resultMap.put("status", "error");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
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
