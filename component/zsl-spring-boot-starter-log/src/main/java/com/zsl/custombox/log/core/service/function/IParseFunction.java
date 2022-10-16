package com.zsl.custombox.log.core.service.function;

/**
 * @Author zsl
 * @Date 2022/5/25 23:32
 * @Email 249269610@qq.com
 */
public interface IParseFunction {

    default boolean executeBefore(){
        return false;
    }

    String functionName();

    String apply(String value);
}
