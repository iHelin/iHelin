package me.ianhe.isite.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.model.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录或token失效时访问接口时，自定义结果的返回
 *
 * @author iHelin
 * @since 2018/6/14 20:57
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(R.error(HttpServletResponse.SC_UNAUTHORIZED, "未认证")));
        out.flush();
        out.close();
    }
}
