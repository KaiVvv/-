package com.briup.cms.common.exception;

import com.briup.cms.common.util.Result;
import com.briup.cms.common.util.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handle(Exception e) {
        // 打印异常跟踪信息到控制台（开发测试阶段临时开启）
        e.printStackTrace();
        if (e instanceof CmsException) {
            return Result.error(((CmsException) e).getResultCode());
        }
        return Result.error(ResultCode.SYSTEM_INNER_ERROR);
    }

}
