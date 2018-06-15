package me.ianhe.isite.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import me.ianhe.isite.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
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
        response.setContentType(Constant.CONTENT_TYPE_JSON);
        PrintWriter out = response.getWriter();
        Map<String, String> res = Maps.newHashMap();
        res.put("status", "error");
        res.put("msg", "未认证");
        out.write(objectMapper.writeValueAsString(res));
        out.flush();
        out.close();
    }
}
