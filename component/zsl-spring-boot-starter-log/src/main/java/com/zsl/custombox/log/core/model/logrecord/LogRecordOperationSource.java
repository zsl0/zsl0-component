package com.zsl.custombox.log.core.model.logrecord;

import com.zsl.custombox.log.core.annotation.LogRecord;
import com.zsl.custombox.log.core.model.resolve.DefaultSpELResolve;
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
 * @author zsl0
 * create on 2022/5/25 22:37
 * email 249269610@qq.com
 */
public class LogRecordOperationSource {
    private Map<AnnotatedElementKey, String> operationCache = new ConcurrentHashMap<>(64);

    @Autowired
    IOperatorGetService operatorGetService;

    /**
     * 处理注解信息，并缓存（注意@see 不理解find多次区别）
     *
     * @See AbstractFallbackCacheOperationSource --> computeCacheOperations(Method, Class)
     */
    public String computeLogRecordOperation(Method method, Class<?> targetClass) {
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        // 获取缓存
        String spEL = operationCache.get(annotatedElementKey);
        if (spEL == null) {
            // 处理注解信息
            LogRecord annotation = method.getAnnotation(LogRecord.class);
            if (annotation != null) {
                spEL = process(annotation);
                operationCache.put(annotatedElementKey, spEL);
            }
        }
        return spEL;
    }


    /**
     * 处理操作日志
     * example：修改账号为#{user.username}
     */
    private String process(LogRecord annotation) {
        String spEL = null;
        String content = annotation.content();
        String failed = annotation.failed();
        String operator = annotation.operator();
        String bizNo = annotation.bizNo();
        String category = annotation.category();
        String detail = annotation.detail();
        String condition = annotation.condition();

        // 使用自定义解析器解析
        DefaultSpELResolve resolve = new DefaultSpELResolve();
        spEL = resolve.resolve(content);

        return spEL;
    }

}
