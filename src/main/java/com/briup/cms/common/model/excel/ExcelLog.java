package com.briup.cms.common.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author YuYan
 * @date 2023-12-12 14:55:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelLog {

    @ExcelProperty(value = "操作用户")
    private String username;
    @ExcelProperty(value = "接口描述信息")
    private String businessName;
    @ExcelProperty(value = "请求接口")
    private String requestUrl;
    @ExcelProperty(value = "请求方式")
    private String requestMethod;
    @ExcelProperty(value = "IP")
    private String requestIp;
    @ExcelProperty(value = "请求接口耗时")
    private Long spendTime;
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ExcelProperty(value = "响应状态码")
    private String code;
    @ExcelProperty(value = "响应消息")
    private String message;



}
