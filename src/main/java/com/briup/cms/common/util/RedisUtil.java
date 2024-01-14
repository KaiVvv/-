package com.briup.cms.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YuYan
 * @date 2023-12-13 09:49:05
 */
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final JsonUtil jsonUtil;

    // public void delete(Object key) {
    //     redisTemplate.delete(key);
    // }

    public void delete(List<?> deleteKeys) {
        Boolean delete = redisTemplate.delete(deleteKeys);
        System.out.println("delete:" + delete);
    }


    public void set(Object key, Object value) {
        redisTemplate.opsForValue()
                .set(key, value);
    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(Object key, Class<T> tClass) {
        return tClass.cast(get(key));
    }

    public String generateKey(Class<?> clazz,
                              String methodName,
                              String argType,
                              Object[] args) {
        Map<String, Object> keyMap = new HashMap<>();
        // keyMap.put("className", className);
        keyMap.put("class", clazz);
        keyMap.put("methodName", methodName);
        keyMap.put("argType", argType);
        keyMap.put("args", args);
        // return keyMap;
        return jsonUtil.stringify(keyMap);
    }

}
