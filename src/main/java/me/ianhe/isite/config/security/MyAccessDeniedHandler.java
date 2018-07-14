package me.ianhe.isite.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import me.ianhe.isite.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * MyAccessDeniedHandler
 *
 * @author iHelin
 * @since 2018/6/6 08:39
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(Constant.CONTENT_TYPE_JSON);
        PrintWriter out = httpServletResponse.getWriter();
        Map<String, String> res = Maps.newHashMap();
        res.put("status", "error");
        res.put("msg", "权限不足，请联系管理员!");
        out.write(objectMapper.writeValueAsString(res));
        out.flush();
        out.close();
    }
}

