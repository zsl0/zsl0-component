package com.zsl0.component.common.core.exception.collector;

/**
 * @author zsl0
 * created on 2023/4/12 14:45
 */
public interface ErrorCollector {

    void collectionError(Throwable t);

}
