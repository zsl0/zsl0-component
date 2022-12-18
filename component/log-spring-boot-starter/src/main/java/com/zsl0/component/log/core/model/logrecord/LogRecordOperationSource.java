package com.zsl0.component.log.core.model.logrecord;

import com.zsl0.component.log.core.annotation.LogRecord;
import com.zsl0.component.log.core.model.resolve.DefaultSpELResolve;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
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
    private final Map<AnnotatedElementKey, String> operationCache = new ConcurrentHashMap<>(64);

    /**
     * 处理注解信息，并缓存spEL解析结果
     * @param method 方法
     * @param targetClass 目标对象
     * @return spEL
     */
    public String computeLogRecordOperation(Method method, Class<?> targetClass) {
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        // 获取缓存
        String spEL = operationCache.get(annotatedElementKey);
        if (spEL == null) {
            // 处理注解信息
            LogRecord annotation = AnnotationUtils.getAnnotation(method, LogRecord.class);
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
