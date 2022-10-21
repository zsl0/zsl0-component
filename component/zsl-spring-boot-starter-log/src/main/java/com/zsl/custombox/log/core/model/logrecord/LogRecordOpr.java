package com.zsl.custombox.log.core.model.logrecord;

/**
 * todo 待升级为将LogRecord注解信息解析为当前对象
 *
 * @author zsl0
 * create on 2022/5/29 11:51
 * email 249269610@qq.com
 */
public class LogRecordOpr {
    private String spEl;
    private boolean function;

    public LogRecordOpr() {
    }

    public LogRecordOpr(String spEl, boolean function) {
        this.spEl = spEl;
        this.function = function;
    }

    public String getSpEl() {
        return spEl;
    }

    public void setSpEl(String spEl) {
        this.spEl = spEl;
    }

    public boolean isFunction() {
        return function;
    }

    public void setFunction(boolean function) {
        this.function = function;
    }
}
