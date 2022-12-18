package com.zsl0.component.common.core.exception.auth.token;

import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * @author zsl0
 * created on 2022/12/12 21:25
 */
public class TokenVerifyFailedException extends AuthCustomException {
    public TokenVerifyFailedException() {
    }

    public TokenVerifyFailedException(String message) {
        super(message);
    }

    public TokenVerifyFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenVerifyFailedException(Throwable cause) {
        super(cause);
    }

    public TokenVerifyFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
