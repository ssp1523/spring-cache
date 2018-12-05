package com.example.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * 自定义缓存管理
 * @author: sunshaoping
 * @date: Create by in 3:51 PM 2018/12/5
 */
public class MyCacheManager implements CacheManager {

    private final CacheManager cacheManager;

    public MyCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Cache getCache(String name) {
        System.out.println("自定义缓存管理器，name=" + name);
        return cacheManager.getCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }
}
