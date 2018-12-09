package com.example;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.time.Duration;

/**
 * 支持redis缓存配置
 * @author: sunshaoping
 * @date: Create by in 6:40 PM 2018/12/7
 * @see Cacheable
 * @see RedisCacheResolver
 */
@SuppressWarnings("SpringCacheNamesInspection")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Cacheable(cacheResolver = "redisCacheResolver")
public @interface RedisCacheable {

    /**
     * 过期时间
     * Examples:
     * <pre>
     *    "PT20.345S" -- parses as "20.345 seconds"
     *    "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
     *    "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
     *    "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
     *    "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
     *    "P-6H3M"    -- parses as "-6 hours and +3 minutes"
     *    "-P6H3M"    -- parses as "-6 hours and -3 minutes"
     *    "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
     * </pre>
     * @return 如果不想用该属性请参考 {@link Cacheable }
     * @see Duration#parse(CharSequence)
     */
    String expires();
    

    /**
     * @see Cacheable#value()
     */
    @AliasFor(annotation = Cacheable.class)
    String[] value() default {};

    /**
     * @see Cacheable#cacheNames()
     */
    @AliasFor(annotation = Cacheable.class)
    String[] cacheNames() default {};

    /**
     * @see Cacheable#key()
     */
    @AliasFor(annotation = Cacheable.class)
    String key() default "";

    /**
     * @see Cacheable#keyGenerator()
     */
    @AliasFor(annotation = Cacheable.class)
    String keyGenerator() default "";

    /**
     * @see Cacheable#cacheManager()
     */
    @AliasFor(annotation = Cacheable.class)
    String cacheManager() default "";

    /**
     * @see Cacheable#condition()
     */
    @AliasFor(annotation = Cacheable.class)
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    @AliasFor(annotation = Cacheable.class)
    String unless() default "";

    /**
     * @see Cacheable#sync()
     */
    @AliasFor(annotation = Cacheable.class)
    boolean sync() default false;

}
