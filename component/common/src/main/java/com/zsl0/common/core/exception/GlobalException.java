package com.zsl0.common.core.exception;

import com.zsl0.common.core.http.ResponseResultStatus;

/**
 * @author zsl0
 * create on 2022/6/1 23:11
 * email 249269610@qq.com
 */
public class GlobalException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    public GlobalException() {
        super();
    }

    public GlobalException(ResponseResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
