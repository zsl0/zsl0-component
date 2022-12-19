package com.zsl0.component.common.core.exception.auth.token;

import com.zsl0.component.common.core.exception.auth.base.AuthCustomException;

/**
 * @author zsl0
 * created on 2022/12/12 21:20
 */
public class TokenGenerateException extends AuthCustomException {


    public TokenGenerateException() {
    }

    public TokenGenerateException(String message) {
        super(message);
    }

    public TokenGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenGenerateException(Throwable cause) {
        super(cause);
    }

    public TokenGenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
