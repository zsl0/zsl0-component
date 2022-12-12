package com.zsl0.component.auth.core.exception.token;

import com.zsl0.component.auth.core.exception.base.CustomException;

/**
 * @author zsl0
 * created on 2022/12/12 21:48
 */
public class TokenExpireException extends CustomException {
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
