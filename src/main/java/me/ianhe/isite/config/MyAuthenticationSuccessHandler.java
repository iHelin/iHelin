package me.ianhe.isite.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianhe.isite.utils.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author iHelin
 * @since 2018/6/6 08:41
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "{\"status\":\"success\",\"data\":"
                + objectMapper.writeValueAsString(SecurityUtils.getCurrentHr()) + "}";
        out.write(s);
        out.flush();
        out.close();
    }
}
