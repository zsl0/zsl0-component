package com.zsl.custombox.common.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Api异常
 *
 * @Author zsl
 * @Date 2022/5/15 19:29
 * @Email 249269610@qq.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private String message;

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
