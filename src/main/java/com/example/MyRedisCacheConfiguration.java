package com.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

/**
 * redis缓存配置
 * @author: sunshaoping
 * @date: Create by in 5:56 PM 2018/12/3
 */
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class MyRedisCacheConfiguration extends CachingConfigurerSupport {

    private final CacheProperties cacheProperties;

    public MyRedisCacheConfiguration(CacheProperties cacheProperties) {this.cacheProperties = cacheProperties;}

    /**
     * 覆盖reids默认配置
     */
    @Bean
    org.springframework.data.redis.cache.RedisCacheConfiguration redisCacheConfiguration() {

        CacheProperties.Redis redisProperties = this.cacheProperties.getRedis();
        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig();
        //序列化
        ObjectMapper objectMapper = new ObjectMapper();
        // 4.设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 5.启动默认的类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)
        );
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        if (!StringUtils.isEmpty(redisProperties.getKeyPrefix())) {

            config = config.computePrefixWith(
                    cacheName -> redisProperties.getKeyPrefix() + ":" + cacheName + ":"
            );
        }

        return config;
    }


}
