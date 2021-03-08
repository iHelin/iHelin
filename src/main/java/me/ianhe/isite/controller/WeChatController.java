package me.ianhe.isite.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.error.WxErrorException;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.model.R;
import me.ianhe.isite.service.UserService;
import me.ianhe.isite.utils.SystemUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    private WxMaService wxMaService;
    @Autowired
    private UserService userService;

    /**
     * 小程序登录
     *
     * @param body
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> body) throws WxErrorException {
        String code = body.get("code");
        if (StringUtils.isEmpty(code)) {
            return R.error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
        }
        WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(code);
        User user = userService.login(body, result.getOpenid(), result.getSessionKey());
        //颁发token
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("avatarUrl", user.getAvatarUrl());
        userInfo.put("enabled", user.isEnabled());
        userInfo.put("username", user.getUsername());
        userInfo.put("idCard", user.getIdCard());
        userInfo.put("telephone", user.getTelephone());
        String token = jwtComponent.createJWT(userInfo, user.getId().toString());
        Map<String, Object> resp = Maps.newHashMap();
        resp.putAll(userInfo);
        resp.put("token", token);
        return R.ok(resp);
    }

    @PostMapping("/binding")
    public R bindUser(@RequestBody Map<String, String> payload) {
        User user = SystemUtils.getCurrentUser();
        user.setEnabled(true);
        user.setUsername(payload.get("username"));
        user.setIdCard(payload.get("idCard"));
        user.setTelephone(payload.get("telephone"));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return R.ok();
    }

    @GetMapping("/reports")
    public R getReport() throws IOException {
        String url = "http://new.sdfyy.cn/OrderReg/newLisList/type/1/blh_no/0003177401/social_no/340827199208104734/patient_id/P301432016-0.html";
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Host", "new.sdfyy.cn");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 11; Mi 10 Pro Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045513 Mobile Safari/537.36 MMWEBID/8735 MicroMessenger/8.0.1.1841(0x28000159) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        headers.put("X-Requested-With", "com.tencent.mm");
        headers.put("Referer", "http://new.sdfyy.cn/OrderReg/reportQuery.html");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cookie", "PHPSESSID=86e10i7665catll5eif6rdh7j7");
        Document document = Jsoup.connect(url).headers(headers).get();
        Elements eles = document.select(".weui_panel");
        List<Map<String, String>> results = Lists.newArrayList();
        for (Element ele : eles) {
            Element titleEle = ele.selectFirst(".weui_media_title");
            System.out.println(titleEle.text());
            Element descEle = ele.selectFirst(".weui_media_desc");
            System.out.println(descEle.text());
            Element timeEle = ele.selectFirst(".weui_panel_ft span");
            System.out.println(timeEle.text());
            Map<String, String> result = Maps.newHashMap();
            result.put("title", titleEle.text());
            result.put("num", descEle.text());
            result.put("time", timeEle.text());
            results.add(result);
        }
        return R.ok(results);
    }

    public static void main(String[] args) throws IOException {
        String url = "http://new.sdfyy.cn/OrderReg/newLisList/type/1/blh_no/0003177401/social_no/340827199208104734/patient_id/P301432016-0.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "new.sdfyy.cn");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 11; Mi 10 Pro Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045513 Mobile Safari/537.36 MMWEBID/8735 MicroMessenger/8.0.1.1841(0x28000159) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        headers.put("X-Requested-With", "com.tencent.mm");
        headers.put("Referer", "http://new.sdfyy.cn/OrderReg/reportQuery.html");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cookie", "PHPSESSID=86e10i7665catll5eif6rdh7j7");
        Document document;
        try {
            document = Jsoup.connect(url).headers(headers).timeout(5000).get();
        } catch (SocketTimeoutException e) {
            document = Jsoup.connect(url).headers(headers).timeout(5000).get();
        }
        Elements eles = document.select(".weui_panel");
        List<Map<String, String>> results = Lists.newArrayList();
        for (Element ele : eles) {
            Element titleEle = ele.selectFirst(".weui_media_title");
            System.out.println(titleEle.text());
            Element descEle = ele.selectFirst(".weui_media_desc");
            System.out.println(descEle.text());
            Element timeEle = ele.selectFirst(".weui_panel_ft span");
            System.out.println(timeEle.text());
            Map<String, String> result = Maps.newHashMap();
            result.put("title", titleEle.text());
            result.put("num", descEle.text());
            result.put("time", timeEle.text());
            results.add(result);
        }
    }

}
