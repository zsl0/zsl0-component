package com.zsl0.component.common.core.exception.auth.authentication;

import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * 认证失败异常
 *
 * @author zsl0
 * created on 2022/12/12 21:57
 */
public class AuthenticationFailedException extends AuthCustomException {
    public AuthenticationFailedException() {
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationFailedException(Throwable cause) {
        super(cause);
    }

    public AuthenticationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
