//package me.ianhe.isite.aspect;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import me.ianhe.isite.exception.SystemException;
//import me.ianhe.isite.service.JwtComponent;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//@Aspect
//@Component
//public class CheckLoginAspect {
//    @Autowired
//    private JwtComponent jwtComponent;
//
//    public static final ThreadLocal<Claims> CLAIMS = new ThreadLocal<>();
//
//    @Around("@annotation(me.ianhe.isite.aspect.CheckLogin)")
//    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            //        1.从header里面获取token
//            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//            if (servletRequestAttributes != null) {
//                HttpServletRequest request = servletRequestAttributes.getRequest();
//                String token = request.getParameter("token");
//                if (StringUtils.isEmpty(token)) {
//                    String authorization = request.getHeader("Authorization");
//                    token = authorization.substring("Bearer".length());
//                }
//                Claims claims = jwtComponent.parseJWT(token);
//                CLAIMS.set(claims);
//                String username = claims.getSubject();
//                Date expiration = claims.getExpiration();
//                if (expiration.before(new Date())) {
//                    throw new SecurityException("Token 已过期");
//                }
//                //        3.如果校验成功，将用户信息设置到request中
//                request.setAttribute("id", claims.get("id"));
//                request.setAttribute("wxNickname", claims.get("wxNickname"));
//                request.setAttribute("role", claims.get("role"));
//            }
//        } catch (ExpiredJwtException e) {
//            throw new SystemException(100, "Token 已过期");
//        } catch (Exception e) {
//            throw new SecurityException("Token 不合法");
//        }
//        Object proceed = joinPoint.proceed();
//        CLAIMS.remove();
//        return proceed;
//    }
//}