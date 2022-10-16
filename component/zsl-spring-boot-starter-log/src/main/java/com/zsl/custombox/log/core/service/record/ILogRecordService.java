package com.zsl.custombox.log.core.service.record;


import com.zsl.custombox.log.core.model.logrecord.LogRecord;

/**
 * @Author zsl
 * @Date 2022/5/25 23:37
 * @Email 249269610@qq.com
 */
public interface ILogRecordService {
    /**
     * 保存 log
     *
     * @param logRecord 日志实体
     */
    void record(LogRecord logRecord);

}
