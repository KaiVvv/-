package com.briup.cms.common.verify;

import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.util.ObjectUtil;
import com.briup.cms.common.util.ResultCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * 参数验证切面
 * @author YuYan
 * @date 2023-12-14 11:20:09
 */
@Aspect
@Component
public class ParamVerifyAspect {

    /**
     * 切入点，作用在Service层所有方法上
     */
    @Pointcut("execution(* com.briup.cms.service..*.*(..))")
    public void serviceMethodPointCut() {

    }

    @Before("serviceMethodPointCut()")
    public void before(JoinPoint joinPoint) {
        /* 通过连接点对象获取到实参列表 */
        Object[] args = joinPoint.getArgs();
        if (ObjectUtil.isEmpty(args)) {
            return;
        }
        /* 遍历校验参数列表中的所有参数 */
        for (Object arg : args) {
            /* 如果为空指针（null值），直接抛出异常 */
            if (ObjectUtil.isEmpty(arg)) {
                throw new CmsException(ResultCode.PARAM_NOT_COMPLETE);
            }
            /* 考虑到各种特殊类型的"空"判断 */
            /* 如果是数组，要求长度不能为0 */
            if (arg.getClass().isArray() && ((Object[]) arg).length == 0) {
                throw new CmsException(ResultCode.PARAM_NOT_COMPLETE);
            }
            /* 如果是集合，要求长度不能为0 */
            if (arg instanceof Collection && ((Collection<?>) arg).size() == 0) {
                throw new CmsException(ResultCode.PARAM_NOT_COMPLETE);
            }
            /* 如果是集合，要求长度不能为0 */
            if (arg instanceof Map && ((Map<?, ?>) arg).size() == 0) {
                throw new CmsException(ResultCode.PARAM_NOT_COMPLETE);
            }
            /* 如果是字符串，要求不能为空串或空白字符构成的字符串 */
            if (arg instanceof String && ObjectUtil.notHasText((String) arg)) {
                throw new CmsException(ResultCode.PARAM_NOT_COMPLETE);
            }
        }
    }

}
