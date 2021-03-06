package me.ianhe.pp.config.security;

import me.ianhe.pp.config.SystemProperties;
import me.ianhe.pp.entity.SysUserEntity;
import me.ianhe.pp.service.JwtComponent;
import me.ianhe.pp.service.SysUserService;
import me.ianhe.pp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtComponent jwtComponent;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private AntPathMatcher antPathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!antPathMatcher.match(Constant.LOGIN_URI, requestURI)) {
            String authorization = request.getHeader(systemProperties.getJwtHeader());
            if (StringUtils.hasText(authorization)) {
                String token = authorization.substring("Bearer".length());
                if (StringUtils.hasText(token)) {
                    String username = jwtComponent.parseJWT(token).getSubject();
                    if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                        SysUserEntity sysUserEntity;
                        String agent = request.getHeader("agent");
                        if ("wechat".equals(agent)) {
                            //来自微信小程序，小程序token里面携带的是用户id
                            sysUserEntity = sysUserService.loadUserById(username);
                        } else {
                            sysUserEntity = sysUserService.loadUserByUsername(username);
                        }
                        if (sysUserEntity != null) {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(sysUserEntity, null, sysUserEntity.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}


