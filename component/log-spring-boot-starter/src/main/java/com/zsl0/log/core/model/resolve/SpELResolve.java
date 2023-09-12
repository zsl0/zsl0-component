package com.zsl0.log.core.model.resolve;

/**
 * @author zsl0
 * created on 2022/12/18 12:06
 */
public interface SpELResolve {

    /**
     * 解析spEL
     * @param content 自定义规则字符串
     * @return 根据自定义规则解析得到的SpEL字符串
     */
    String resolve(String content);

}
