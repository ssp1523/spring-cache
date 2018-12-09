package com.example;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * redis缓存
 * @author: sunshaoping
 * @date: Create by in 11:51 AM 2018/12/9
 */
public class RedisCache extends org.springframework.data.redis.cache.RedisCache {

    public RedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
    }
}
