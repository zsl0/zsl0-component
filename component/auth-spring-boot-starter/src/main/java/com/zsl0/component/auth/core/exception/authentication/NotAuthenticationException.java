package com.zsl0.component.auth.core.exception.authentication;

import com.zsl0.component.auth.core.exception.base.CustomException;

/**
 * 未认证异常
 *
 * @author zsl0
 * created on 2022/12/12 21:57
 */
public class NotAuthenticationException extends CustomException {
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
