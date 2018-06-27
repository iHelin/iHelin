package me.ianhe.isite.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import me.ianhe.isite.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author iHelin
 * @since 2018/6/6 08:39
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(Constant.CONTENT_TYPE_JSON);
        PrintWriter out = httpServletResponse.getWriter();
        Map<String, String> res = Maps.newHashMap();
        res.put("status", "error");
        if (e instanceof UsernameNotFoundException) {
            res.put("msg", e.getMessage());
        }
        if (e instanceof BadCredentialsException) {
            res.put("msg", "用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            res.put("msg", "账户被禁用，登录失败，请联系管理员!");
        } else {
            res.put("msg", e.getMessage());
        }
        out.write(objectMapper.writeValueAsString(res));
        out.flush();
        out.close();
    }
}
