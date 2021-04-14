package me.ianhe.isite.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.date.DateTime;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.error.WxErrorException;
import me.ianhe.isite.entity.SysUserEntity;
import me.ianhe.isite.service.SysUserService;
import me.ianhe.isite.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private SysUserService sysUserService;

    /**
     * 小程序登录
     *
     * @param body
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> body) throws WxErrorException {
        String code = body.get("code");
        String nickname = body.get("nickname");
        String avatarUrl = body.get("avatarUrl");
        if (StringUtils.isEmpty(code)) {
            return R.error("登录失败");
        }
        WxMaUserService wxUserService = wxMaService.getUserService();
        WxMaJscode2SessionResult result = wxUserService.getSessionInfo(code);
        String openid = result.getOpenid();
        String sessionKey = result.getSessionKey();
        SysUserEntity user = sysUserService.login(nickname, avatarUrl, openid, sessionKey);
        //颁发token
        String token = jwtComponent.createJWT(user.getId().toString());
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("id", user.getId());
        resultMap.put("binding", user.getBinding());
        resultMap.put("username", user.getUsername());
        resultMap.put("idCard", user.getIdCard());
        resultMap.put("mobile", user.getMobile());
        resultMap.put("token", token);
        return R.ok(resultMap);
    }

    /**
     * 绑定用户信息
     *
     * @param payload
     * @param token
     * @return
     */
    @PostMapping("/binding")
    public R binding(@RequestBody Map<String, String> payload, UsernamePasswordAuthenticationToken token) {
        SysUserEntity user = (SysUserEntity) token.getPrincipal();
        user.setBinding(true);
        user.setUsername(payload.get("username"));
        user.setIdCard(payload.get("idCard"));
        user.setMobile(payload.get("mobile"));
        user.setUpdateTime(DateTime.now());
        sysUserService.updateById(user);
        return R.ok();
    }

    @GetMapping("/me")
    public R loginInfo(UsernamePasswordAuthenticationToken token) {
        return R.ok(token.getPrincipal());
    }

}
