//package me.ianhe.isite.config.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Maps;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Map;
//
///**
// * @author iHelin
// * @since 2018/6/6 08:41
// */
//@Component
//public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
//                                        HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        PrintWriter out = httpServletResponse.getWriter();
//        Map<String, Object> res = Maps.newHashMap();
//        res.put("status", "success");
//        res.put("data", authentication);
//        out.write(objectMapper.writeValueAsString(res));
//        out.flush();
//        out.close();
//    }
//
//}
