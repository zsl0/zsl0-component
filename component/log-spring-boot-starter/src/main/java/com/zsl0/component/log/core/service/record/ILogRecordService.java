package com.zsl0.component.log.core.service.record;

/**
 * @author zsl0
 * create on 2022/5/25 23:37
 * email 249269610@qq.com
 */
public interface ILogRecordService {
    /**
     * 保存 log
     */
    void record(String logRecord);

    Long getUserId();

}
