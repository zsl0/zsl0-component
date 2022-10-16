package com.zsl.custombox.authentication.service.cache.impl;

import com.zsl.custombox.authentication.model.enumbean.CachePrefix;
import com.zsl.custombox.common.core.service.cache.CacheServer;
import com.zsl.custombox.common.core.service.cache.TokenServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * token缓存实现类
 *
 * @Author zsl
 * @Date 2022/6/9 17:26
 * @Email 249269610@qq.com
 */
@Service
public class TokenServerImpl implements TokenServer {

    @Autowired
    CacheServer cacheServer;

    @Override
    public void set(String key, String value) {
        cacheServer.set(CachePrefix.TOKEN.getPrefix() + key, value);
    }

    @Override
    public String get(String key) {
        return cacheServer.get(CachePrefix.TOKEN.getPrefix() + key);
    }

    @Override
    public void delete(String key) {
        cacheServer.delete(CachePrefix.TOKEN.getPrefix() + key);
    }

    @Override
    public void expire(String key, long timeout) {
        cacheServer.expire(CachePrefix.TOKEN.getPrefix() + key, timeout);
    }

    @Override
    public boolean contains(String key) {
        return cacheServer.contains(CachePrefix.TOKEN.getPrefix() + key);
    }
}
