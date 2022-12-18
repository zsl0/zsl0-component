package com.zsl0.component.log.core.service.record;

import com.zsl0.component.log.core.model.logrecord.LogRecordInfo;

/**
 * @author zsl0
 * create on 2022/5/25 23:37
 * email 249269610@qq.com
 */
public interface ILogRecordService {

    /**
     * 保存 log
     * @param logRecord 日志实体
     */
    default void record(LogRecordInfo logRecord) {
        record(logRecord.getLogInfo());
    }

    /**
     * 保存 log
     */
    void record(String logRecord);

    Long getUserId();

}
