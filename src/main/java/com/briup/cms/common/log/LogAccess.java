package com.briup.cms.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YuYan
 * @date 2023-11-27 16:12:01
 */
/* 当前注解只允许被使用在方法上 */
@Target(ElementType.METHOD)
/* 注解的保留策略：RUNTIME */
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAccess {

    /* 属性：描述方法的功能 */
    String value() default "未命名功能";

}
