package com.zsl0.component.common.core.exception.auth.token;


import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * @author zsl0
 * created on 2022/12/12 21:48
 */
public class TokenExpireException extends AuthCustomException {
    public TokenExpireException() {
    }

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpireException(Throwable cause) {
        super(cause);
    }

    public TokenExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
