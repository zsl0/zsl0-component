package com.zsl0.demo.authentication.service.cache.impl;

import com.zsl0.demo.authentication.model.enumbean.CachePrefix;
import com.zsl0.component.common.core.service.cache.CacheServer;
import com.zsl0.component.common.core.service.cache.TokenServer;

/**
 * token缓存实现类
 *
 * @author zsl0
 * create on 2022/6/9 17:26
 * email 249269610@qq.com
 */
//@Service
public class TokenServerImpl implements TokenServer {

    @Override
    public void set(CacheServer cache, String key, String value) {
        cache.set(CachePrefix.TOKEN.getPrefix() + key, value);
    }

    @Override
    public String get(CacheServer cache, String key) {
        return cache.get(CachePrefix.TOKEN.getPrefix() + key);
    }

    @Override
    public void delete(CacheServer cache, String key) {
        cache.delete(CachePrefix.TOKEN.getPrefix() + key);
    }

    @Override
    public void expire(CacheServer cache, String key, long timeout) {
        cache.expire(CachePrefix.TOKEN.getPrefix() + key, timeout);
    }

    @Override
    public boolean contains(CacheServer cache, String key) {
        return cache.contains(CachePrefix.TOKEN.getPrefix() + key);
    }
}
