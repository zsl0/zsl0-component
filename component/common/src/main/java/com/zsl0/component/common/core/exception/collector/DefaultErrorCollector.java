package com.zsl0.component.common.core.exception.collector;

import com.zsl0.util.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * 默认打印输出
 *
 * @author zsl0
 * created on 2023/4/12 14:45
 */
@Component
@ConditionalOnMissingBean(ErrorCollector.class)
public class DefaultErrorCollector implements ErrorCollector {

    private final Logger log = LoggerFactory.getLogger(DefaultErrorCollector.class);

    public void collectionError(Throwable t) {
        log.error(LogUtil.getLogStackTrace(t));
    }

}
