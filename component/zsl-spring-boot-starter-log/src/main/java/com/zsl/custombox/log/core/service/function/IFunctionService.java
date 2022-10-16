package com.zsl.custombox.log.core.service.function;

/**
 * @Author zsl
 * @Date 2022/5/25 23:36
 * @Email 249269610@qq.com
 */
public interface IFunctionService {
    String apply(String functionName, String value);

    boolean beforeFunction(String functionName);
}
