//package com.zsl0.component.common.core.service.cache.impl;
//
//import com.zsl0.component.common.core.service.cache.CacheServer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zsl0
// * create on 2022/10/17 20:50
// */
//@Service
//public class CacheServerImpl implements CacheServer {
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    @Override
//    public void set(String key, String value) {
//        stringRedisTemplate.opsForValue().set(key, value);
//    }
//
//    @Override
//    public void set(String key, String value, long timeout) {
//        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public String get(String key) {
//        return stringRedisTemplate.opsForValue().get(key);
//    }
//
//    @Override
//    public void setObject(Object key, Object value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    @Override
//    public void setObject(Object key, Object value, long timeout) {
//        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public Object getObject(Object key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    @Override
//    public void delete(String key) {
//        ;
//    }
//
//    @Override
//    public void expire(String key, long timeout) {
//        redisTemplate.opsForValue();
//        stringRedisTemplate.opsForValue();
//    }
//
//    @Override
//    public boolean contains(String key) {
//        return false;
//    }
//}
