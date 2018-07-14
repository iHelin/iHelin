package me.ianhe.isite.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author iHelin
 * @since 2018/7/9 21:54
 */
public class CaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 70956154974506374L;

    public CaptchaException(String msg) {
        super(msg);
    }
}
