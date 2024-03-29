package com.zsl0.log.core.service.record;

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
        // 默认使用日志记录
        log.info(logRecord);
    }

    @Override
    public String getUserId() {
        return "0";
    }
}