package com.example;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * redis缓存配置
 * @author: sunshaoping
 * @date: Create by in 5:56 PM 2018/12/3
 */
@EnableCaching
@EnableConfigurationProperties({RedisCacheProperties.class, CacheProperties.class})
public class RedisCacheConfig implements RedisCachePrefix {

    private final RedisSerializer<String> serializer = new StringRedisSerializer();

    private final RedisCacheProperties redisCacheProperties;

    private final CacheProperties cacheProperties;

    public RedisCacheConfig(RedisCacheProperties redisCacheProperties, CacheProperties cacheProperties) {
        this.redisCacheProperties = redisCacheProperties;
        this.cacheProperties = cacheProperties;
    }
    /**
     * 覆盖redis 默认缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = redisTemplate(redisConnectionFactory);

        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate, null, redisCacheProperties.isCacheNullValues());
        if (redisCacheProperties.getKeyPrefix() != null) {
            cacheManager.setUsePrefix(true);
        }
        cacheManager.setCachePrefix(this);
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }
        if (redisCacheProperties.getDefaultExpiration() != null) {
            cacheManager.setDefaultExpiration(redisCacheProperties.getDefaultExpiration());
        }
        if (!CollectionUtils.isEmpty(redisCacheProperties.getExpires())) {
            cacheManager.setExpires(
                    redisCacheProperties.getExpires()
            );
        }
        return cacheManager;
    }

    private RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 重写缓存前缀序列化
     */
    @Override
    public byte[] prefix(String cacheName) {
        return serializer.serialize(redisCacheProperties.getKeyPrefix() + ":" + cacheName + ":");
    }

}
