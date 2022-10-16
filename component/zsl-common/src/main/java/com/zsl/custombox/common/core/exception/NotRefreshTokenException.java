package com.zsl.custombox.common.core.exception;

/**
 * 不是refresh_token异常
 *
 * @Author zsl
 * @Date 2022/5/15 20:45
 * @Email 249269610@qq.com
 */
public class NotRefreshTokenException extends AbstractAuthenticationException {
    public NotRefreshTokenException(String msg) {
        super(msg);
    }
}
