package com.example;

import com.example.service.MyCacheManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 缓存配置
 * @author: sunshaoping
 * @date: Create by in 16:52 2018-11-16
 */
@Configurable
public class CacheConfiguration {
    /**
     * 默认缓存管理器
     */
    @Bean
    @Primary
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager();
    }

    /**
     * 自定义缓存管理器
     */
    @Bean
    public MyCacheManager myCacheManager() {
        return new MyCacheManager(new ConcurrentMapCacheManager());
    }


}
