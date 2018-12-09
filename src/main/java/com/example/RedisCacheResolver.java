package com.example;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * redis 缓存解析器
 * @author: sunshaoping
 * @date: Create by in 11:37 AM 2018/12/9
 * @see RedisCacheable
 */
public class RedisCacheResolver extends SimpleCacheResolver {

    private final RedisCacheWriter cacheWriter;

    private final RedisCacheConfiguration redisCacheConfiguration;

    private final ConcurrentMap<Method, Collection<? extends Cache>> cacheMethodMap = new ConcurrentHashMap<>();

    public RedisCacheResolver(RedisCacheWriter cacheWriter, CacheManager cacheManager, RedisCacheConfiguration redisCacheConfiguration) {
        super(cacheManager);
        this.cacheWriter = cacheWriter;
        this.redisCacheConfiguration = redisCacheConfiguration;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {

        RedisCacheable redisCacheable = AnnotationUtils.findAnnotation(context.getMethod(), RedisCacheable.class);
        if (redisCacheable == null) {
            return super.resolveCaches(context);
        }
        return cacheMethodMap.computeIfAbsent(context.getMethod(), method -> getCaches(context, redisCacheable));
    }

    private Collection<? extends Cache> getCaches(CacheOperationInvocationContext<?> context, RedisCacheable redisCacheable) {
        Collection<String> cacheNames = getCacheNames(context);
        if (cacheNames == null) {
            return Collections.emptyList();
        }
        Collection<Cache> result = new ArrayList<>(cacheNames.size());
        String expires = redisCacheable.expires();
        ConversionService conversionService = redisCacheConfiguration.getConversionService();
        Duration ttl = conversionService.convert(expires, Duration.class);
        for (String cacheName : cacheNames) {
            Cache cache = createRedisCache(cacheName, redisCacheConfiguration.entryTtl(ttl));
            if (cache == null) {
                throw new IllegalArgumentException("Cannot find cache named '" +
                        cacheName + "' for " + context.getOperation());
            }
            result.add(cache);
        }

        return result;
    }

    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new RedisCache(name, cacheWriter, cacheConfig);
    }
}
