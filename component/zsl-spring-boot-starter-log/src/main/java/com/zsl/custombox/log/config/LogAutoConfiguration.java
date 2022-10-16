package com.zsl.custombox.log.config;

import com.zsl.custombox.log.core.interceptor.AccessLogInterceptor;
import com.zsl.custombox.log.core.model.logrecord.LogRecordOperationSource;
import com.zsl.custombox.log.core.service.record.DefaultLogRecordServiceImpl;
import com.zsl.custombox.log.core.service.record.ILogRecordService;
import com.zsl.custombox.log.core.service.function.DefaultFunctionServiceImpl;
import com.zsl.custombox.log.core.service.function.IFunctionService;
import com.zsl.custombox.log.core.service.function.IParseFunction;
import com.zsl.custombox.log.core.service.function.ParseFunctionFactory;
import com.zsl.custombox.log.core.service.operator.DefaultOperatorGetServiceImpl;
import com.zsl.custombox.log.core.service.operator.IOperatorGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 日志自动配置，AccessLogInterceptor(访问日志)、LogRecordAspect(操作日志)
 *
 * @Author zsl
 * @Date 2022/5/22 21:04
 * @Email 249269610@qq.com
 */
@Configuration
public class LogAutoConfiguration implements WebMvcConfigurer {

    // ==========================   拦截器   ==========================
    @Bean
    public AccessLogInterceptor accessLogInterceptor() {
        return new AccessLogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.accessLogInterceptor());
    }

    // ==========================   操作日志容器   ==========================

    // 处理注解信息操作源
    @Bean
    public LogRecordOperationSource logRecordOperationSource () {
        return new LogRecordOperationSource();
    }

    // 自定义函数
    @Bean
    @ConditionalOnMissingBean(IFunctionService.class)
    public IFunctionService functionService(ParseFunctionFactory parseFunctionFactory) {
        return new DefaultFunctionServiceImpl(parseFunctionFactory);
    }

    @Bean
    public ParseFunctionFactory parseFunctionFactory(@Autowired List<IParseFunction> list) {
        return new ParseFunctionFactory(list);
    }

    @Bean
    @ConditionalOnMissingBean(IParseFunction.class)
    public IParseFunction defaultParseFunction() {
        return new IParseFunction() {
            @Override
            public String functionName() {
                return null;
            }

            @Override
            public String apply(String value) {
                return null;
            }
        };
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
