package com.briup.cms.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YuYan
 * @date 2023-11-27 15:11:07
 */
public class RequestInfoHolder {

    private static final ThreadLocal<Map<String, Object>>
            threadLocal = new ThreadLocal<>();

    public static void set(String key, Object value) {
        /* 判断当前线程在ThreadLocal中的Map集合是否已被创建 */
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Long getLong(String key) {
        Object value = get(key);
        if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }

    public static String getString(String key) {
        Object value = get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public static Object get(String key) {
        /* 从ThreadLocal对象中取出当前线程的Map  */
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            /* 该线程还尚未向ThreadLocal中存储数据 */
            return null;
        }
        return map.get(key);
    }




}
