package me.ianhe.isite.controller;

import me.ianhe.isite.exception.SystemException;
import me.ianhe.isite.model.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * @author iHelin
 * @since 2017/9/20 11:39
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(SystemException e, Locale locale) {
        return R.error(e.getMessage());
    }
}
