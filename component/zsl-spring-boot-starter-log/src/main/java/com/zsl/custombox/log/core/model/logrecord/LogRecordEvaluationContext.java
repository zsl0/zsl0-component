package com.zsl.custombox.log.core.model.logrecord;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * SpEL上下文
 *
 * @Author zsl
 * @Date 2022/5/24 21:20
 * @Email 249269610@qq.com
 */
public class LogRecordEvaluationContext extends MethodBasedEvaluationContext {

    /**
     * 将操作日志上下文，存储到SpEL上下文
     */
    public LogRecordEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer, Object ret, String errMsg) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && variables.size() > 0) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                setVariable(entry.getKey(), entry.getValue());
            }
        }
        setVariable("_ret", ret);
        setVariable("_errMsg", errMsg);
    }
}
