package com.zsl0.common.core.service.cache;

/**
 * @author zsl0
 * create on 2021/12/31 10:00
 */
public interface CodeServer {
    void set(CacheServer cache, String key, String value);

    String get(CacheServer cache, String key);

    void del(CacheServer cache, String key);
}
