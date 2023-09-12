package com.zsl0.common.core.exception.auth.authentication;

import com.zsl0.common.core.exception.auth.base.AuthCustomException;

/**
 * 未认证异常
 *
 * @author zsl0
 * created on 2022/12/12 21:57
 */
public class NotAuthenticationException extends AuthCustomException {
    public NotAuthenticationException() {
    }

    public NotAuthenticationException(String message) {
        super(message);
    }

    public NotAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthenticationException(Throwable cause) {
        super(cause);
    }

    public NotAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
