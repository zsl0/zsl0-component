package com.zsl.custombox.common.core.exception;

/**
 * 用于异常处理捕捉
 *
 * @author zsl0
 * create on 2022/5/22 20:29
 * email 249269610@qq.com
 */
public abstract class AbstractAuthenticationException extends CustomException {
    public AbstractAuthenticationException() {
    }

    public AbstractAuthenticationException(String msg) {
        super(msg);
    }

    public AbstractAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
