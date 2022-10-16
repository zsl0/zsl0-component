package com.zsl.custombox.log.core.aspect;

import com.zsl.custombox.log.core.annotation.LogRecord;
import com.zsl.custombox.log.core.model.logrecord.LogRecordContext;
import com.zsl.custombox.log.core.model.MethodExceptionResult;
import com.zsl.custombox.log.core.model.logrecord.LogRecordExpressionEvaluator;
import com.zsl.custombox.log.core.model.logrecord.LogRecordValueParser;
import com.zsl.custombox.log.core.service.record.ILogRecordService;
import com.zsl.custombox.log.core.service.function.IFunctionService;
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
 * @Author zsl
 * @Date 2022/5/23 23:20
 * @Email 249269610@qq.com
 */
@Aspect
@Component
public class LogRecordAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    ILogRecordService logRecordService;

    @Autowired
    IFunctionService functionService;

    LogRecordValueParser logRecordValueParser = new LogRecordValueParser(new LogRecordExpressionEvaluator(), functionService);

    @Around("@annotation(com.zsl.custombox.log.core.annotation.LogRecord)")
    public Object logRecord(ProceedingJoinPoint point) throws Throwable {
        return execute(point, point.getTarget().getClass(), ((MethodSignature) point.getSignature()).getMethod(), point.getArgs());
    }

    /**
     * v0.0.1 版本 简单使用SpEL解析(在后面版本中提供以函数式方式解析变量)
     */
    private Object execute(ProceedingJoinPoint point, Class<?> targetClass, Method method, Object[] args) throws Throwable {
        Object ret = null;

        LogRecordContext.putEmptySpan();

        MethodExceptionResult exceptionResult = new MethodExceptionResult(true, null, "");
        // 拆分注解信息存储为List等待执行
        /**
         * List     Operations          operations
         * List     String              spElTemplates
         * Map      <String, String>    functionNameAndReturnMap
         *
         *         operations = logRecordOperationSource.computeLogRecordOperations(method, targetClass);
         *         List<String> spElTemplates = getBeforeExecuteFunctionTemplate(operations);
         *         //业务逻辑执行前的自定义函数解析
         *         functionNameAndReturnMap = processBeforeExecuteFunctionTemplate(spElTemplates, targetClass, method, args);
         *
         *         if (!CollectionUtils.isEmpty(operations)) {
         *             recordExecute(ret, method, args, operations, targetClass,
         *                     methodExecuteResult.isSuccess(), methodExecuteResult.getErrorMsg(), functionNameAndReturnMap);
         *         }
         */

        // 执行List中自定义函数
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
        // 创建上下文
        EvaluationContext evaluationContext = logRecordValueParser.createEvaluationContext(method, args, ret, errorMsg);
        // 获取评估后expressionString
        String expression = logRecordValueParser.getExpression(spEL, new AnnotatedElementKey(method, targetClass), evaluationContext);
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
