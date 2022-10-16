package com.zsl.custombox.common.core.http;

import lombok.Data;

/**
 * 自定义返回结果集
 *
 * @Author zsl
 * @Date 2022/5/15 18:55
 * @Email 249269610@qq.com
 */
@Data
public class ResponseResult<T> {
    private Integer code;
    private String msg;
    private T data;

    private ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private static <T> ResponseResult<T> newInstance(ResponseResultStatus status, T data) {
        return newInstance(status.getCode(), status.getMsg(), data);
    }

    private static <T> ResponseResult<T> newInstance(Integer code, String msg, T data) {
        return new ResponseResult<>(code, msg, data);
    }

    /**
     * 操作成功
     */
    public static ResponseResult<Void> success() {
        return success(null);
    }

    /**
     * 操作成功，自定义返回数据
     */
    public static <T> ResponseResult<T> success(T data) {
        return newInstance(ResponseResultStatus.SUCCESS, data);
    }

    /**
     * 操作失败
     */
    public static ResponseResult<Void> failed() {
        return success(null);
    }

    /**
     * 操作失败，自定义返回数据
     */
    public static <T> ResponseResult<T> failed(T data) {
        return newInstance(ResponseResultStatus.BAD_REQUEST, data);
    }

    public static <T> ResponseResult<T> custom(Integer code, String msg, T data) {
        return newInstance(code, msg, data);
    }

    public static <T> ResponseResult<T> custom(ResponseResultStatus status, T data) {
        return newInstance(status.getCode(), status.getMsg(), data);
    }

}
