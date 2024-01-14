package com.briup.cms.common.util;

import lombok.Getter;

/**
 * 统一给前端返回的响应数据结构
 * @author YuYan
 * @date 2023-11-14 14:24:59
 */
@Getter
public class Result {

    /**
     * 响应状态码
     */
    private final int code;
    /**
     * 响应信息
     */
    private final String message;
    /**
     * 响应数据
     */
    private final Object data;

    private Result(ResultCode resultCode) {
        this(resultCode, null);
    }
    private Result(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }
    public static Result ok() {
        return new Result(ResultCode.SUCCESS);
    }
    public static Result ok(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }
    public static Result error(ResultCode resultCode) {
        return new Result(resultCode);
    }


}
