package com.zsl0.common.core.exception.auth.base;

/**
 * @author zsl0
 * created on 2022/12/12 21:20
 */
public abstract class AuthCustomException extends RuntimeException {

    public AuthCustomException() {
    }

    public AuthCustomException(String message) {
        super(message);
    }

    public AuthCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthCustomException(Throwable cause) {
        super(cause);
    }

    public AuthCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
