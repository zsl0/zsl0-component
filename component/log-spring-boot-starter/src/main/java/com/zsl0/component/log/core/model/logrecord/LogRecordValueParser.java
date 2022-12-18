package com.zsl0.component.log.core.model.logrecord;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @author zsl0
 * create on 2022/5/25 21:57
 * email 249269610@qq.com
 */
public class LogRecordValueParser {
    private LogRecordExpressionEvaluator expressionEvaluator;

    public LogRecordValueParser() {
    }

    public LogRecordValueParser(LogRecordExpressionEvaluator expressionEvaluator) {
        this.expressionEvaluator = expressionEvaluator;
    }

    /**
     * 创建评估上下文，提供给ExpressionParser解析Expression使用
     */
    public EvaluationContext createEvaluationContext(Method method, Object[] arguments, Object ret, String errMsg) {
        return new LogRecordEvaluationContext(null, method, arguments, new DefaultParameterNameDiscoverer(), ret, errMsg);
    }

    public String getExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return expressionEvaluator.getExpression(conditionExpression, methodKey, evalContext);
    }

}
