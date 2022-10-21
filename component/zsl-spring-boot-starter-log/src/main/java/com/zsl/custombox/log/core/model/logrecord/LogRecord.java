package com.zsl.custombox.log.core.model.logrecord;

/**
 * @author zsl0
 * create on 2022/5/29 12:44
 * email 249269610@qq.com
 */
public class LogRecord {
    private String logInfo;

    public LogRecord() {
    }

    public LogRecord(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }
}
