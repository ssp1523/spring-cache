package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author: sunshaoping
 * @date: Create by in 5:20 PM 2018/12/3
 */
@ConfigurationProperties("spring.cache.redis")
public class RedisCacheProperties {

    /**
     * 全局默认过期时间 单位秒
     */
    private Long defaultExpiration;

    /**
     * 是否缓存null值
     */
    private boolean cacheNullValues = true;

    /**
     * 缓存前缀
     */
    private String keyPrefix = "spring-cache";

    /**
     * 指定缓存名称过期时间 单位秒
     */
    private Map<String, Long> expires;

    public Long getDefaultExpiration() {
        return defaultExpiration;
    }

    public void setDefaultExpiration(Long defaultExpiration) {
        this.defaultExpiration = defaultExpiration;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Map<String, Long> getExpires() {
        return expires;
    }

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }
}
