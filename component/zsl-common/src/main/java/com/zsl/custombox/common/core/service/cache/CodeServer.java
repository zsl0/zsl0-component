package com.zsl.custombox.common.core.service.cache;

/**
 * @author zsl0
 * create on 2021/12/31 10:00
 */
public interface CodeServer {
    void set(String key, String value);

    String get(String key);

    void del(String key);
}
