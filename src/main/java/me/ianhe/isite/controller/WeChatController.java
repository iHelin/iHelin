package me.ianhe.isite.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import me.chanjar.weixin.common.error.WxErrorException;
import me.ianhe.isite.aspect.CheckLogin;
import me.ianhe.isite.aspect.CheckLoginAspect;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.model.R;
import me.ianhe.isite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        String token = jwtComponent.createJWT(userInfo, user.getNickname(), user.getId().toString());
        Map<String, Object> resp = Maps.newHashMap();
        resp.putAll(userInfo);
        resp.put("token", token);
        return R.ok(resp);
    }

    /**
     * 小程序获取登录用户信息
     *
     * @return
     */
    @CheckLogin
    @GetMapping("/me")
    public R auth() {
        Claims claims = CheckLoginAspect.CLAIMS.get();
        String id = claims.getId();
        User user = userService.getById(Integer.valueOf(id));
        return R.ok(user);
    }

    @CheckLogin
    @PostMapping("/binding")
    public R bindUser(@RequestBody Map<String, String> payload) {
        Claims claims = CheckLoginAspect.CLAIMS.get();
        String id = claims.getId();
        User user = userService.getById(Integer.valueOf(id));
        user.setEnabled(true);
        user.setUsername(payload.get("username"));
        user.setIdCard(payload.get("idCard"));
        user.setTelephone(payload.get("telephone"));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return R.ok();
    }

}
