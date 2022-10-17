package com.zsl.custombox.common.core.exception;

/**
 * 不是access_token异常
 *
 * @author zsl0
 * create on 2022/5/15 20:45
 * email 249269610@qq.com
 */
public class NotAccessTokenException extends AbstractAuthenticationException {
    public NotAccessTokenException(String msg) {
        super(msg);
    }
}
