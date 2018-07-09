package me.ianhe.isite.config.security;

import me.ianhe.isite.controller.CommonController;
import me.ianhe.isite.exception.CaptchaException;
import me.ianhe.isite.model.CaptchaCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2018/7/9 21:51
 */
public class CaptchaFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(request.getRequestURI(), "/login")
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            try {
                validate(request);
            } catch (CaptchaException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 验证验证码
     *
     * @param request
     * @throws ServletRequestBindingException
     */
    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        HttpSession session = request.getSession();
        CaptchaCode captchaCode = (CaptchaCode) session.getAttribute(CommonController.CAPTCHA_CODE_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(request, captchaParameter);
        if (StringUtils.isBlank(codeInRequest)) {
            throw new CaptchaException("验证码的值不能为空");
        }
        if (captchaCode == null) {
            throw new CaptchaException("验证码不存在");
        }
        if (captchaCode.isExpried()) {
            throw new CaptchaException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(codeInRequest, captchaCode.getCode())) {
            throw new CaptchaException("验证码不匹配");
        }
        session.removeAttribute(CommonController.CAPTCHA_CODE_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
