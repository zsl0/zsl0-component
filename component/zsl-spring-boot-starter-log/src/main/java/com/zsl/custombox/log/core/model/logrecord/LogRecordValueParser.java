package com.zsl.custombox.log.core.model.logrecord;

import com.zsl.custombox.log.core.service.function.IFunctionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @Author zsl
 * @Date 2022/5/25 21:57
 * @Email 249269610@qq.com
 */
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordValueParser {
    private LogRecordExpressionEvaluator expressionEvaluator;
    private IFunctionService functionService;

    /**
     * 创建评估上下文，提供给ExpressionParser解析Expression使用
     */
    public EvaluationContext createEvaluationContext(Method method, Object[] arguments, Object ret, String errMsg) {
        return new LogRecordEvaluationContext(null, method, arguments, new DefaultParameterNameDiscoverer(), ret, errMsg);
    }

    public String getExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return expressionEvaluator.getExpression(conditionExpression, methodKey, evalContext);
    }

    public String apply(String functionName, String value) {
        return functionService.apply(functionName, value);
    }

    public boolean beforeFunction(String functionName) {
        return functionService.beforeFunction(functionName);
    }

}
