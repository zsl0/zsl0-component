package com.zsl.custombox.log.core.aspect;

import com.zsl.custombox.log.core.annotation.LogRecord;
import com.zsl.custombox.log.core.model.MethodExceptionResult;
import com.zsl.custombox.log.core.model.logrecord.LogRecordContext;
import com.zsl.custombox.log.core.model.logrecord.LogRecordExpressionEvaluator;
import com.zsl.custombox.log.core.model.logrecord.LogRecordValueParser;
import com.zsl.custombox.log.core.service.record.ILogRecordService;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zsl0
 * create on 2022/5/23 23:20
 * email 249269610@qq.com
 */
@Aspect
@Component
public class LogRecordAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ILogRecordService logRecordService;

    LogRecordValueParser logRecordValueParser = new LogRecordValueParser(new LogRecordExpressionEvaluator());

    @Around("@annotation(com.zsl.custombox.log.core.annotation.LogRecord)")
    public Object logRecord(ProceedingJoinPoint point) throws Throwable {
        return execute(point, point.getTarget().getClass(), ((MethodSignature) point.getSignature()).getMethod(), point.getArgs());
    }

    /**
     * v0.0.1 版本 简单使用SpEL解析(在后面版本中提供以函数式方式解析变量)
     */
    private Object execute(ProceedingJoinPoint point, Class<?> targetClass, Method method, Object[] args) throws Throwable {
        Object ret = null;

        // 获取日志EL上下文
        LogRecordContext.putEmptySpan();

        // 方法执行结果集
        MethodExceptionResult exceptionResult = new MethodExceptionResult(true, null, "");

        // 获取注解spEL表达式
        String spEL = getAnnotation(method).content();

        try {
            ret = point.proceed();
        } catch (Throwable throwable) {
            exceptionResult = new MethodExceptionResult(false, throwable, throwable.getMessage());
        }
        // 解析List中SpEL
        try {
            // 存储日志
            if (Strings.isNotBlank(spEL)) {
                recordExecute(ret, method, args, spEL, targetClass,
                        exceptionResult.isSuccess(), exceptionResult.getErrorMsg());
            }
        } catch (Throwable t) {
            log.error("记录操作日志失败，不影响业务执行！", t);
        } finally {
            // 清理 Context
            LogRecordContext.clear();
        }
        // 执行失败将异常抛出
        if (!exceptionResult.isSuccess()) {
            throw exceptionResult.getThrowable();
        }
        return ret;
    }

    private void recordExecute(Object ret, Method method, Object[] args, String spEL, Class<?> targetClass, boolean success, String errorMsg) {
        String expression = null;

        // 方法执行成功
        if (success) {
            // 创建上下文
            EvaluationContext evaluationContext = logRecordValueParser.createEvaluationContext(method, args, ret, errorMsg);
            // 获取评估后expressionString
            expression = logRecordValueParser.getExpression(spEL, new AnnotatedElementKey(method, targetClass), evaluationContext);
        } else {
            // 方法执行失败
            expression = String.format("方法执行失败，errorMessages=%s", errorMsg);
        }

        // 持久化操作日志
        logRecordService.record(new com.zsl.custombox.log.core.model.logrecord.LogRecord(expression));
    }

    /**
     * 获取方法
     */
    private Method getMethod(ProceedingJoinPoint point) {
        return ((MethodSignature) point.getSignature()).getMethod();
    }

    /**
     * 获取注解
     */
    private LogRecord getAnnotation(Method method) {
        LogRecord annotation = method.getAnnotation(LogRecord.class);
        return annotation;
    }
}
