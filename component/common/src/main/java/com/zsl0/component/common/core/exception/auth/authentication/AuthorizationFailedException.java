package com.zsl0.component.common.core.exception.auth.authentication;

import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * 授权失败异常
 *
 * @author zsl0
 * created on 2022/12/12 22:19
 */
public class AuthorizationFailedException extends AuthCustomException {
    public AuthorizationFailedException() {
    }

    public AuthorizationFailedException(String message) {
        super(message);
    }

    public AuthorizationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationFailedException(Throwable cause) {
        super(cause);
    }

    public AuthorizationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
