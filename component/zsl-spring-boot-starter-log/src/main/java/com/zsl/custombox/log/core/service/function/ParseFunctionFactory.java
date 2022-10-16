package com.zsl.custombox.log.core.service.function;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zsl
 * @Date 2022/5/25 23:34
 * @Email 249269610@qq.com
 */
public class ParseFunctionFactory {
    private Map<String, IParseFunction> allFunctionMap;

    public ParseFunctionFactory(List<IParseFunction> parseFunctions) {
        if (CollectionUtils.isEmpty(parseFunctions)) {
            return;
        }
        allFunctionMap = new HashMap<>();
        for (IParseFunction parseFunction : parseFunctions) {
            if (StringUtils.isEmpty(parseFunction.functionName())) {
                continue;
            }
            allFunctionMap.put(parseFunction.functionName(), parseFunction);
        }
    }

    public IParseFunction getFunction(String functionName) {
        return allFunctionMap.get(functionName);
    }

    public boolean isBeforeFunction(String functionName) {
        return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).executeBefore();
    }
}
