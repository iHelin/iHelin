package me.ianhe.pp.config.security;

import cn.hutool.json.JSONUtil;
import me.ianhe.pp.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录或token失效时访问接口时，自定义结果的返回
 *
 * @author iHelin
 * @since 2018/6/14 20:57
 */
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        //认证异常全部转为正常请求，通过状态码返回
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(JSONUtil.toJsonStr(R.error(HttpServletResponse.SC_UNAUTHORIZED, "未授权")));
    }
}
