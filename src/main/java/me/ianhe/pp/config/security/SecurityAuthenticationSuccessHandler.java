package me.ianhe.pp.config.security;

import cn.hutool.json.JSONUtil;
import me.ianhe.pp.entity.SysUserEntity;
import me.ianhe.pp.service.JwtComponent;
import me.ianhe.pp.service.SysUserService;
import me.ianhe.pp.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2019/10/29 8:57 PM
 *
 * @author: zhouximin
 * @Description:
 **/
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtComponent jwtComponent;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取登录用户信息
        SysUserEntity userDetails = (SysUserEntity) authentication.getPrincipal();
        // 生成token
        String jwtToken = jwtComponent.createJWT(userDetails.getUsername());
        logger.debug("create jwt token:{}", jwtToken);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSONUtil.toJsonStr(R.ok().putData(jwtToken)));
    }
}
