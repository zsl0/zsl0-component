package com.zsl0.component.common.core.service.cache.impl;

import com.zsl0.component.common.core.service.cache.CacheServer;
import com.zsl0.component.common.core.service.cache.TokenServer;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zsl0
 * create on 2021/12/30 11:59
 */
//@Component
public class TokenServerImpl implements TokenServer {

    @Value("${token.timeout:1800}")
    long timeout;

    private final static String PRE_TOKEN = "admin:token:";

    @Override
    public void set(CacheServer cache, String key, String value) {
        cache.set(PRE_TOKEN  + key, value, timeout);
    }

    @Override
    public String get(CacheServer cache, String key) {
        return cache.get(PRE_TOKEN  + key);
    }

    @Override
    public void delete(CacheServer cache, String key) {
        cache.delete(PRE_TOKEN  + key);
    }

    @Override
    public void expire(CacheServer cache, String key, long timeout) {
        cache.expire(PRE_TOKEN  + key, timeout);
    }

    @Override
    public boolean contains(CacheServer cache, String key) {
        return cache.contains(PRE_TOKEN  + key);
    }
}
