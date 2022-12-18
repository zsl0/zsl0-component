package com.zsl0.component.log.core.model.logrecord;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zsl0
 * create on 2022/5/29 12:44
 * email 249269610@qq.com
 */
public class LogRecordInfo {
    private String logInfo;

    public LogRecordInfo() {
    }

    public LogRecordInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }
}
