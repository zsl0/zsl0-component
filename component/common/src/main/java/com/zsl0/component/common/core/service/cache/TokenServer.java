package com.zsl0.component.common.core.service.cache;

/**
 * @author zsl0
 * create on 2021/12/30 11:53
 */
public interface TokenServer {
    /**
     * 保存token
     */
    void set(CacheServer cache, String key, String value);

    /**
     * 获取token
     */
    String get(CacheServer cache, String key);

    /**
     * 删除token
     */
    void delete(CacheServer cache, String key);

    /**
     * 设置过期时间
     */
    void expire(CacheServer cache, String key, long timeout);

    /**
     * 判断是否存在
     */
    boolean contains(CacheServer cache, String key);

}
