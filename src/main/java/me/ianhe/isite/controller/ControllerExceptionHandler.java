package me.ianhe.isite.controller;

import me.ianhe.isite.exception.SystemException;
import me.ianhe.isite.model.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author iHelin
 * @since 2017/9/20 11:39
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(SystemException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleSystemException(SystemException e) {
        logger.warn("SystemException", e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 捕获所有异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Throwable e) {
        logger.error("Unknown Error", e);
        return R.error(e.getMessage());
    }
}
