package me.ianhe.isite.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.error.WxErrorException;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.model.R;
import me.ianhe.isite.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
        String token = jwtComponent.createJWT(user.getId().toString());
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("id", user.getId());
        resultMap.put("binding", user.getBinding());
        resultMap.put("username", user.getUsername());
        resultMap.put("idCard", user.getIdCard());
        resultMap.put("telephone", user.getTelephone());
        resultMap.put("token", token);
        return R.ok(resultMap);
    }

    @PostMapping("/binding")
    public R bindUser(@RequestBody Map<String, String> payload, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        user.setBinding(true);
        user.setUsername(payload.get("username"));
        user.setIdCard(payload.get("idCard"));
        user.setTelephone(payload.get("telephone"));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return R.ok();
    }

}
