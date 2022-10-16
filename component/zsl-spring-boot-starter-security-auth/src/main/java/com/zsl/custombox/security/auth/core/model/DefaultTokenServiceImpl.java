package com.zsl.custombox.security.auth.core.model;

import com.zsl.custombox.common.core.service.cache.TokenServer;
import com.zsl.custombox.common.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token令牌存储服务
 *
 * @Author zsl
 * @Date 2022/6/2 10:05
 * @Email 249269610@qq.com
 */
public class DefaultTokenServiceImpl implements TokenServer {
    private final Map<String, Value> tokenCache = new ConcurrentHashMap<>(64);

    private static Long VALID_TIME = 60 * 60 * 24 * 3L;

    @Override
    public void set(String key, String value) {
        checkCapacity();
        Long expire = TokenUtil.getExpire(value);
        Value val = createValue(value, expire);
        tokenCache.put(key, val);
    }

    @Override
    public String get(String key) {
        Value value = tokenCache.get(key);
        String token = null;
        if (value == null || isExpire(value)) {
            tokenCache.remove(key);
        } else {
            token = value.getToken();
        }
        return token;
    }

    @Override
    public void delete(String key) {
        tokenCache.remove(key);
    }

    @Override
    public void expire(String key, long timeout) {
        Value value = tokenCache.get(key);
        value.setExpire(timeout);
    }

    @Override
    public boolean contains(String key) {
        return tokenCache.get(key) != null;
    }

    private Value createValue(String token, Long expire) {
        return new Value(token, expire);
    }

    /**
     * 超过
     */
    private void checkCapacity() {
        if (tokenCache.size() >> 13 > 0) {
            tokenCache.clear();
        }
    }

    /**
     * 根据当前时间，获取下一过期时间
     */
    private Long getExpire() {
        return System.currentTimeMillis() + VALID_TIME * 1000;
    }

    /**
     * 判断Value是否过期
     */
    private boolean isExpire(Value value) {
        return value.getExpire() < System.currentTimeMillis();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Value {
        private String token;
        private Long expire;
    }
}
