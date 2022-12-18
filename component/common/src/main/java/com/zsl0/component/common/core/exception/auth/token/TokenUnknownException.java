package com.zsl0.component.common.core.exception.auth.token;

import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * @author zsl0
 * created on 2022/12/13 21:05
 */
public class TokenUnknownException extends AuthCustomException {
    public TokenUnknownException() {
    }

    public TokenUnknownException(String message) {
        super(message);
    }

    public TokenUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenUnknownException(Throwable cause) {
        super(cause);
    }

    public TokenUnknownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
