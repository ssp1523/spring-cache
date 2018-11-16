package com.example.service;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 缓存key值逻辑处理
 * @author: sunshaoping
 * @date: Create by in 15:25 2018-11-16
 */
@Component
public class MyKeyGenerator implements KeyGenerator {


    @Override
    public Object generate(Object target, Method method, Object... params) {
        System.out.println(target);
        System.out.println(method);
        return params[0];
    }
}
