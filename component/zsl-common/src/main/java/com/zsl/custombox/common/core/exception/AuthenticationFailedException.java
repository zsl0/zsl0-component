package com.zsl.custombox.common.core.exception;

/**
 * 认证失败异常
 *
 * @Author zsl
 * @Date 2022/5/15 20:22
 * @Email 249269610@qq.com
 */
public class AuthenticationFailedException extends AbstractAuthenticationException {
    public AuthenticationFailedException() {
    }

    public AuthenticationFailedException(String msg) {
        super(msg);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
