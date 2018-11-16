package com.example;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;

import java.util.List;

/**
 * 缓存配置
 * @author: sunshaoping
 * @date: Create by in 16:52 2018-11-16
 */
@Configurable
public class CacheConfiguration {

    /**
     * 缓存管理 配置
     */
    //    @Bean
    public CompositeCacheManager compositeCacheManager(List<CacheManager> cacheManagers) {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(cacheManagers);
        compositeCacheManager.setFallbackToNoOpCache(true);
        return compositeCacheManager;
    }


}
