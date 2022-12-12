package com.zsl0.component.log.core.service.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zsl0
 * create on 2022/5/25 23:38
 * email 249269610@qq.com
 */
public class DefaultLogRecordServiceImpl implements ILogRecordService {

    static Logger log = LoggerFactory.getLogger(DefaultLogRecordServiceImpl.class);

    @Override
    public void record(String logRecord) {
        // 默认使用日志记录 todo 自实现记录方式
        log.info(logRecord);
    }

    @Override
    public Long getUserId() {
        return 0l;
    }
}