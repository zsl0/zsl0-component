package com.zsl.custombox.log.core.model.logrecord;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析对象
 *
 * @Author zsl
 * @Date 2022/5/25 21:49
 * @Email 249269610@qq.com
 */
public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {
    private Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public String getExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(expressionCache, methodKey, conditionExpression).getValue(evalContext, String.class);
    }
}
