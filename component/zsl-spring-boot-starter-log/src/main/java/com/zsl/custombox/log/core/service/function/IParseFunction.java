package com.zsl.custombox.log.core.service.function;

/**
 * @author zsl0
 * create on 2022/5/25 23:32
 * email 249269610@qq.com
 */
public interface IParseFunction {

    default boolean executeBefore(){
        return false;
    }

    String functionName();

    String apply(String value);
}
