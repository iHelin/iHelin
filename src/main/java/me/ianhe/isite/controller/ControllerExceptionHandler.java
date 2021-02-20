package me.ianhe.isite.controller;

import me.ianhe.isite.exception.SystemException;
import me.ianhe.isite.model.R;
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

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleSystemException(SystemException e) {
        return R.error(e.getMessage());
    }

    /**
     * 捕获所有异常
     *
     * @param e
     * @return
     */
//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public R handleException(Throwable e) {
//        return R.error(e.getMessage());
//    }
}
