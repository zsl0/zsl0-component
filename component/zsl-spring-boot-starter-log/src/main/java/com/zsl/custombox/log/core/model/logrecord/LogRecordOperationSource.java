package com.zsl.custombox.log.core.model.logrecord;

import com.zsl.custombox.log.core.annotation.LogRecord;
import com.zsl.custombox.log.core.service.operator.IOperatorGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理注解信息操作源
 *
 * @Author zsl
 * @Date 2022/5/25 22:37
 * @Email 249269610@qq.com
 */
public class LogRecordOperationSource {
    private Map<AnnotatedElementKey, List<LogRecordOpr>> operationCache = new ConcurrentHashMap<>(64);

    @Autowired
    IOperatorGetService operatorGetService;

    /**
     * 处理注解信息，并缓存（注意@see 不理解find多次区别）
     *
     * @See AbstractFallbackCacheOperationSource --> computeCacheOperations(Method, Class)
     */
    public List<LogRecordOpr> computeLogRecordOperation(Method method, Class<?> targetClass) {
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        // 获取缓存
        List<LogRecordOpr> value = operationCache.get(annotatedElementKey);
        if (value == null) {
            // 处理注解信息
            LogRecord annotation = method.getAnnotation(LogRecord.class);
            if (annotation != null) {
                value = process(annotation);
                operationCache.put(annotatedElementKey, value);
            }
        }
        return value;
    }


    /**
     * 处理操作日志
     */
    private List<LogRecordOpr> process(LogRecord annotation) {
        StringBuilder sb = new StringBuilder();
        List<LogRecordOpr> oprs = new ArrayList<>();

        String operator = annotation.operator();
        if ("".equals(operator)) {
            // 获取当前UserID
            operator = operatorGetService.getUser().getUserId().toString();
        } else {
            operator = annotation.operator();
        }
        oprs.add(new LogRecordOpr(String.format("用户：%s,", operator), false));


        oprs.add(new LogRecordOpr(String.format("操作：%s,", annotation.bizNo()), false));

        String content = annotation.content();



        // todo 处理@LogRecord其它信息

        return oprs;
    }

}
