package com.zsl.custombox.log.config;

import com.zsl.custombox.log.core.model.logrecord.LogRecordOperationSource;
import com.zsl.custombox.log.core.service.operator.DefaultOperatorGetServiceImpl;
import com.zsl.custombox.log.core.service.operator.IOperatorGetService;
import com.zsl.custombox.log.core.service.record.DefaultLogRecordServiceImpl;
import com.zsl.custombox.log.core.service.record.ILogRecordService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志自动配置，AccessLogInterceptor(访问日志)、LogRecordAspect(操作日志)
 *
 * @author zsl0
 * create on 2022/5/22 21:04
 * email 249269610@qq.com
 */
@Configuration
public class LogAutoConfiguration implements WebMvcConfigurer {

    // ==========================   操作日志容器   ==========================

    // 处理注解信息操作源
    @Bean
    public LogRecordOperationSource logRecordOperationSource () {
        LogRecordOperationSource logRecordOperationSource = new LogRecordOperationSource();
        return logRecordOperationSource;
    }

    // 持久化
    @Bean
    @ConditionalOnMissingBean(ILogRecordService.class)
    public ILogRecordService logRecordService() {
        return new DefaultLogRecordServiceImpl();
    }

    // 操作人
    @Bean
    @ConditionalOnMissingBean(IOperatorGetService.class)
    public IOperatorGetService operatorGetService() {
        return new DefaultOperatorGetServiceImpl();
    }

}
