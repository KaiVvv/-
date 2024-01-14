package com.briup.cms.common.exception;

import com.briup.cms.common.util.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author YuYan
 * @date 2023-11-29 10:10:14
 */
@Getter
@RequiredArgsConstructor
public class CmsException extends RuntimeException {

    // 定义一个属性，保存异常的种类信息
    private final ResultCode resultCode;

}
