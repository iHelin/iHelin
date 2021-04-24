package me.ianhe.pp.controller.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.error.WxErrorException;
import me.ianhe.pp.controller.BaseController;
import me.ianhe.pp.entity.SysUserEntity;
import me.ianhe.pp.service.SysUserService;
import me.ianhe.pp.utils.R;
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
    public R login(@RequestBody JSONObject body) throws WxErrorException {
        String code = body.getStr("code");
        String nickname = body.getStr("nickname");
        String avatarUrl = body.getStr("avatarUrl");
        Integer gender = body.getInt("gender");
        String country = body.getStr("country");
        String province = body.getStr("province");
        String city = body.getStr("city");
        String language = body.getStr("language");
        if (StringUtils.isEmpty(code)) {
            return R.error("登录失败");
        }
        WxMaUserService wxUserService = wxMaService.getUserService();
        WxMaJscode2SessionResult result = wxUserService.getSessionInfo(code);
        String openid = result.getOpenid();
        String sessionKey = result.getSessionKey();
        SysUserEntity user = sysUserService.login(nickname, avatarUrl, openid, sessionKey, gender, country,
                province, city, language);
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
    public R binding(@RequestBody Map<String, String> payload,
                     UsernamePasswordAuthenticationToken token) {
        SysUserEntity user = (SysUserEntity) token.getPrincipal();
        user.setBinding(true);
        user.setUsername(payload.get("username"));
        user.setIdCard(payload.get("idCard"));
        user.setMobile(payload.get("mobile"));
        user.setUpdateTime(DateTime.now());
        sysUserService.updateById(user);
        return R.ok();
    }

    /**
     * 用户基本信息
     *
     * @param token
     * @return
     */
    @GetMapping("/me")
    public R loginInfo(UsernamePasswordAuthenticationToken token) {
        return R.ok(token.getPrincipal());
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    @GetMapping("/now")
    public R now() {
        return R.ok(System.currentTimeMillis());
    }

}
