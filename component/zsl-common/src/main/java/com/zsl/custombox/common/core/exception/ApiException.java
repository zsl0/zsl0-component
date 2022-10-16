package com.zsl.custombox.common.core.exception;

/**
 * @Author zsl
 * @Date 2022/5/15 20:07
 * @Email 249269610@qq.com
 */
public class ApiException extends CustomException {
    public ApiException() {
    }

    public ApiException(String msg) {
        super(msg);
    }
}
