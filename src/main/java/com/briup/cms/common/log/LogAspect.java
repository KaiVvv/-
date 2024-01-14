package com.briup.cms.common.log;

import com.briup.cms.common.model.entity.Log;
import com.briup.cms.common.util.JsonUtil;
import com.briup.cms.common.util.ObjectUtil;
import com.briup.cms.common.util.RequestInfoHolder;
import com.briup.cms.service.LogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面
 * @author YuYan
 * @date 2023-11-27 15:25:42
 */
@Component
@Aspect
@RequiredArgsConstructor
public class LogAspect {


    private final LogService logService;

    private final JsonUtil jsonUtil;

    /*
     * 使用一个方法定义切入点
     * 1、controller包下的方法需要被代理输出日志
     * 2、必须添加@LogAccess注解
     */
    @Pointcut("execution(* com.briup.cms.web.controller..*.*(..)) " +
            "&& @annotation(LogAccess)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        /* 第一阶段：目标方法执行前的前置操作 */

        /* 获取调用方法传入的参数 */
        Object[] args = proceedingJoinPoint.getArgs();
        /* 记录方法开始执行前的当前时间 */
        long startTime = System.currentTimeMillis();
        /* 第二阶段：执行目标方法，获取返回值 */
        Object returnValue = proceedingJoinPoint.proceed();
        /* 第三阶段：目标方法执行后的后置操作 */
        /* 记录目标方法执行后的当前时间 */
        long endTime = System.currentTimeMillis();

        /* 通过连接点获取到方法的签名对象、方法镜像，通过镜像获取注解的属性值 */
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogAccess logAccess = method.getAnnotation(LogAccess.class);
        String businessName = null;
        if (ObjectUtil.nonNull(logAccess)) {
            businessName = logAccess.value();
        }

        /* 从ThreadLocal中取出其余的日志信息 */
        /* 将日志信息封装为一个日志实体对象 */
        Log log = Log.builder()
                .username(RequestInfoHolder.getString("username"))
                .requestMethod(RequestInfoHolder.getString("requestMethod"))
                .requestUrl(RequestInfoHolder.getString("requestUrl"))
                .requestIp(RequestInfoHolder.getString("requestIp"))
                .requestSource(RequestInfoHolder.getString("requestSource"))
                .businessName(businessName)
                .spendTime(endTime - startTime)
                .requestParams(jsonUtil.stringify(args))
                .responseResult(jsonUtil.stringify(returnValue))
                .createTime(new Date())
                .build();

        /* 执行写入 */
        System.out.println("LogAspect：" + Thread.currentThread().getName());
        logService.save(log);


        // /* 执行目标方法，获取到返回值 */
        // Object returnValue = proceedingJoinPoint.proceed();
        //
        //
        // /* 通过连接点，获取到方法的签名对象 */
        // MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // /* 通过签名对象，获取到目标方法的镜像 */
        // Method method = signature.getMethod();
        // /* 通过目标方法镜像对象，获取到LogAccess注解中的属性值（功能名称） */
        // LogAccess logAccess = method.getAnnotation(LogAccess.class);
        // /* 如果方法确实存在该注解，则记录日志 */
        // if (logAccess != null) {
        //     /* 从注解中提取出功能名称 */
        //     String business = logAccess.value();
        //     /* 通过连接点对象获取调用方法时传入的参数 */
        //     Object[] args = proceedingJoinPoint.getArgs();
        //     /* 将参数转换为JSON字符串 */
        //     String requestParameters = JsonUtil.parse(args);
        //     /* 操作执行时间设置为当前时间 */
        //     Date time = new Date();
        //     /* 从ThreadLocal中取出拦截器存入的相关信息（请求者、请求对象） */
        //     String username = LogInfoHolder.getString("username");
        //     String realname = LogInfoHolder.getString("realname");
        //     String telephone = LogInfoHolder.getString("telephone");
        //     String requestMethod = LogInfoHolder.getString("requestMethod");
        //     String requestUri = LogInfoHolder.getString("requestUri");
        //     String requestAddress = LogInfoHolder.getString("requestAddress");
        //     /* 将所有的信息封装为一个日志PO对象 */
        //     LogRecord record = new LogRecord(null, business, requestMethod,
        //             requestUri, requestAddress, requestParameters,
        //             username, realname, telephone, time);
        //     /* 获取日志组件，执行日志的插入 */
        //     logPersisting.persist(record);
        // }
        // return returnValue;
        return returnValue;
    }

}
