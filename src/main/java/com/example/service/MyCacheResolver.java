package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.support.NoOpCache;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义 CacheResolver
 * @author: sunshaoping
 * @date: Create by in 5:53 PM 2018/12/5
 */
@Component
@ConfigurationProperties("spring.cache.no-op-cache")
public class MyCacheResolver extends SimpleCacheResolver implements CacheResolver {


    private final Map<String, NoOpCache> noOpCacheMap = new HashMap<>();

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        Set<String> cacheNames = context.getOperation().getCacheNames();
        for (String cacheName : cacheNames) {
            if (noOpCacheMap.containsKey(cacheName)) {
                return Collections.singletonList(noOpCacheMap.get(cacheName));
            }
        }
        return super.resolveCaches(context);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        return null;
    }

    public void setList(List<String> list) {
        for (String name : list) {
            noOpCacheMap.put(name, new NoOpCache(name));
        }

    }

    @Autowired
    @Override
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
    }
}
