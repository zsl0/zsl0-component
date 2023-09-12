package com.zsl0.log.core.model;

/**
 * 方法异常结果
 *
 * @author zsl0
 * create on 2022/5/23 23:56
 * email 249269610@qq.com
 */
public class MethodExceptionResult {
    private boolean success;
    private Throwable throwable;
    private String errorMsg;

    public MethodExceptionResult() {
    }

    public MethodExceptionResult(boolean success, Throwable throwable, String errorMsg) {
        this.success = success;
        this.throwable = throwable;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
