package com.zsl0.auth.config.log;

import com.zsl0.component.auth.core.model.AuthInfo;
import com.zsl0.component.auth.core.util.SecurityContextHolder;
import com.zsl0.component.log.core.service.record.ILogRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zsl0
 * created on 2022/12/19 19:51
 */
@Component
public class MyLogRecordServiceImpl implements ILogRecordService {

    Logger log = LoggerFactory.getLogger(MyLogRecordServiceImpl.class);

    @Override
    public void record(String logRecord) {
        log.info(logRecord);
    }

    @Override
    public String getUserId() {
        // todo 自定义获取当前用户唯一凭证
        return Optional.ofNullable(SecurityContextHolder.getAuth())
                .map(AuthInfo::getUuid)
                .orElse("0");
    }
}
