package com.zsl.custombox.common.core.exception;

/**
 * 没有认证异常
 *
 * @author zsl0
 * create on 2022/6/1 23:07
 * email 249269610@qq.com
 */
public class NotAuthenticationException extends AbstractAuthenticationException {

    public NotAuthenticationException() {
        super();
    }

    public NotAuthenticationException(String msg) {
        super(msg);
    }

    public NotAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
