package com.zsl0.common.core.exception.auth.authentication;

import com.zsl0.common.core.exception.auth.base.AuthCustomException;

/**
 * 未授权异常
 *
 * @author zsl0
 * created on 2022/12/12 22:19
 */
public class NotAuthorizationException extends AuthCustomException {
    public NotAuthorizationException() {
    }

    public NotAuthorizationException(String message) {
        super(message);
    }

    public NotAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizationException(Throwable cause) {
        super(cause);
    }

    public NotAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
