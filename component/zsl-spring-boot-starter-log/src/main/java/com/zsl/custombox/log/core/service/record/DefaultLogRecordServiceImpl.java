package com.zsl.custombox.log.core.service.record;

import com.zsl.custombox.log.core.model.logrecord.LogRecord;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zsl0
 * create on 2022/5/25 23:38
 * email 249269610@qq.com
 */
@Slf4j
public class DefaultLogRecordServiceImpl implements ILogRecordService {

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(LogRecord logRecord) {
        log.info(logRecord.getLogInfo());
    }
}