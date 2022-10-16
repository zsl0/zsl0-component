package com.zsl.custombox.common.core.exception;

/**
 * 没有认证异常
 *
 * @Author zsl
 * @Date 2022/6/1 23:07
 * @Email 249269610@qq.com
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
