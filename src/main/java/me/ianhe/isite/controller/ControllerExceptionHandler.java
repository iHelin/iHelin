package me.ianhe.isite.controller;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/9/20 11:39
 */
//@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleException(Exception e, Locale locale) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", "error");
        res.put("data", e.getMessage());
        return res;
    }
}
