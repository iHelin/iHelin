package me.ianhe.isite.exception;

/**
 * 系统异常
 *
 * @author iHelin
 * @since 2017/6/6 17:21
 */
public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }

    public SystemException(String s) {
        super(s);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}