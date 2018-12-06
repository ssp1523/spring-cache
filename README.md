此配置适用于`Spring-boot1.5.x`版本。

添加以下几项自定义配置

- `default-expiration`  默认过期时间 单位秒
- `cache-null-values` 缓存null值
- `key-prefix`  全局缓存前缀 默认值 `spring-cache`
- `expires` 指定缓存名称过期时间 单位秒

properties配置类，省略Get/Set方法

```java
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
}
```

覆盖Redis默认配置

```java
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
```

配置缓存失效时间 20秒、前缀`redis-cache`

```yaml
spring:
  redis:
    host: localhost
    port: 6379
  cache:
    redis:
      key-prefix: redis-cache
      default-expiration: 20
    type: redis
```

运行单元测试

```java
com.example.service.BookServiceTest#findBook
```

Redis缓存服务会多一条缓存记录，缓存时间是20秒

```shell
127.0.0.1:6379> keys *
1) "redis-cache:books:1234-5678"
127.0.0.1:6379> ttl "redis-cache:books:1234-5678"
(integer) 19
```