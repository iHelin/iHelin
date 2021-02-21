package me.ianhe.isite.aspect;

import io.jsonwebtoken.Claims;
import me.ianhe.isite.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class CheckLoginAspect {
    public static final ThreadLocal<Claims> CLAIMS = new ThreadLocal<>();

    @Around("@annotation(me.ianhe.isite.aspect.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //        1.从header里面获取token
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            if (servletRequestAttributes != null) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                String authorization = request.getHeader("Authorization");
                if (StringUtils.isNotEmpty(authorization)) {
                    String token = authorization.substring("Bearer".length());
                    Claims claims = JwtUtil.parseJWT(token);
//                    request.setAttribute("claims", claims);
                    CLAIMS.set(claims);
                    String username = claims.getSubject();
                    Date expiration = claims.getExpiration();
                    if (expiration.before(new Date())) {
                        throw new SecurityException("Token 已过期");
                    }
                    //        3.如果校验成功，将用户信息设置到request中
                    request.setAttribute("id", claims.get("id"));
                    request.setAttribute("wxNickname", claims.get("wxNickname"));
                    request.setAttribute("role", claims.get("role"));
                }


            }
        } catch (Exception e) {
            throw new SecurityException("Token 不合法");
        }
        Object proceed = joinPoint.proceed();
        CLAIMS.remove();
        return proceed;
    }
}
