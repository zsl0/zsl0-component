package com.zsl0.common.core.service.cache.impl;

import com.zsl0.common.core.service.cache.CacheServer;
import com.zsl0.common.core.service.cache.CodeServer;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zsl0
 * create on 2021/12/31 10:01
 */
//@Component
public class CodeServerImpl implements CodeServer {
    @Value("${spring.mail.codeExpire:3}")
    long codeExpire;

    private static final String PRE_EMAIL_CODE = "admin:emailCode:";

    @Override
    public void set(CacheServer cache, String key, String value) {
        cache.set(PRE_EMAIL_CODE + key, value, codeExpire * 60);
    }

    @Override
    public String get(CacheServer cache, String key) {
        return cache.get(PRE_EMAIL_CODE + key);
    }

    @Override
    public void del(CacheServer cache, String key) {
        cache.delete(PRE_EMAIL_CODE + key);
    }
}
