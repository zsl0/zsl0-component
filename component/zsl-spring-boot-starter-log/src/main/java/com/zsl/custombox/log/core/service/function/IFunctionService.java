package com.zsl.custombox.log.core.service.function;

/**
 * @author zsl0
 * create on 2022/5/25 23:36
 * email 249269610@qq.com
 */
public interface IFunctionService {
    String apply(String functionName, String value);

    boolean beforeFunction(String functionName);
}
