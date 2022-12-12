package com.zsl0.component.auth.core.exception.token;

import com.zsl0.component.auth.core.exception.base.CustomException;

/**
 * @author zsl0
 * created on 2022/12/12 21:25
 */
public class TokenVerifyFailedException extends CustomException {
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
