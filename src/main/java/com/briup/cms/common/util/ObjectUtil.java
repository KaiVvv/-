package com.briup.cms.common.util;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 工具类 - 处理对象相关
 * @author YuYan
 * @date 2023-11-30 11:05:06
 */
public class ObjectUtil {

    public static boolean equalsIgnoreCase(String s1, String s2) {
        return !isNull(s1) && s1.equalsIgnoreCase(s2);
    }

    /**
     * 判断多个字符串中是否有任意一个为空
     * 1）为null值
     * 2）为""串
     * 3）为完全由空白字符构成的字符串
     * @param strs
     * @return 如果可变参数组中有至少一个为空或空白内容的字符串就会返回true，
     *         所有的参数都不为空，则返回false
     */
    public static boolean anyNotHasText(String...strs) {
        if (ObjectUtil.isNull(strs)) {
            return true;
        }
        for (String str : strs) {
            if (!StringUtils.hasText(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasText(String s) {
        return StringUtils.hasText(s);
    }

    public static boolean notHasText(String str) {
        return !StringUtils.hasText(str);
    }


    public static boolean notEquals(String s1, String s2) {
        return !equals(s1, s2);
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    public static boolean equals(Object o1, Object o2) {
        /* 如果两个对象的地址已经相同，则直接返回true */
        if (o1 == o2) {
            return true;
        }
        /* 如果o1为null，则直接返回false（因为o2不可能也为null） */
        if (isNull(o1)) {
            return false;
        }
        return o1.equals(o2);
    }

    /**
     * 判断某个对象是否为空指针
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断某个对象是否不为空指针
     * @param obj
     * @return
     */
    public static boolean nonNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断传入的对象是否为空
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }
    /**
     * 判断传入的对象是否为空
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray()) {
            return ((Object[]) obj).length == 0;
        }
        return false;
    }
    /**
     * 判断传入的对象是否不为空
     * @return
     */
    public static boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    /**
     * 判断传入的对象是否不为空
     * @return
     */
    public static boolean nonEmpty(Object obj) {
        return !isEmpty(obj);
    }

}
