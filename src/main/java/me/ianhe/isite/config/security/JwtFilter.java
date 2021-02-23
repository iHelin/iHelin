package me.ianhe.isite.config.security;

import io.jsonwebtoken.Claims;
import me.ianhe.isite.entity.User;
import me.ianhe.isite.service.JwtComponent;
import me.ianhe.isite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2021/1/27 22:51
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final ThreadLocal<User> LOGIN_USER = new ThreadLocal<>();

    @Autowired
    private UserService userService;
    @Autowired
    private JwtComponent jwtComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            String authorization = request.getHeader("Authorization");
            if (StringUtils.hasText(authorization)) {
                token = authorization.substring("Bearer".length());
            }
        }
        if (StringUtils.hasText(token)) {
            Claims claims = jwtComponent.parseJWT(token);
            String id = claims.getSubject();
            //token 存在但未登录
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getById(id);
                request.setAttribute("loginUser", user);
                LOGIN_USER.set(user);
                UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
        LOGIN_USER.remove();
    }
}
