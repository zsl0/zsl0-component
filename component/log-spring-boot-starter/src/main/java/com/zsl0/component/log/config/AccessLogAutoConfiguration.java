package com.zsl0.component.log.config;

import com.zsl0.component.log.core.interceptor.AccessLogInterceptor;
import com.zsl0.component.log.core.model.logrecord.LogRecordOperationSource;
import com.zsl0.component.log.core.service.record.DefaultLogRecordServiceImpl;
import com.zsl0.component.log.core.service.record.ILogRecordService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志自动配置，AccessLogInterceptor(访问日志)、LogRecordAspect(操作日志)
 *
 * @author zsl0
 * create on 2022/5/22 21:04
 * email 249269610@qq.com
 */
@Configuration
@ComponentScan("com.zsl0.component.log")
@Order(100)
public class AccessLogAutoConfiguration implements WebMvcConfigurer {

    // ==========================   拦截器   ==========================
    @Bean
    public AccessLogInterceptor accessLogInterceptor() {
        return new AccessLogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.accessLogInterceptor());
    }

    /**
     * 持久化
     * 需要自实现:
     *  1.日志存储方式
     *  2.获取用户id
     */
    @Bean
    @ConditionalOnMissingBean(ILogRecordService.class)
    public ILogRecordService logRecordService() {
        return new DefaultLogRecordServiceImpl();
    }

    // 处理注解信息操作源
    @Bean
    public LogRecordOperationSource logRecordOperationSource () {
        LogRecordOperationSource logRecordOperationSource = new LogRecordOperationSource();
        return logRecordOperationSource;
    }

//    /**
//     * 操作人
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(IOperatorGetService.class)
//    public IOperatorGetService operatorGetService() {
//        return new DefaultOperatorGetServiceImpl();
//    }

}
