package com.briup.cms.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 实体类 - 日志
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_log")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Log {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "business_name")
    String businessName;
    @TableField(value = "request_url")
    String requestUrl;
    @TableField(value = "request_method")
    String requestMethod;
    @TableField(value = "ip")
    String requestIp;
    @TableField(value = "source")
    String requestSource;
    @TableField(value = "params_json")
    String requestParams;
    @TableField(value = "result_json")
    String responseResult;
    @TableField(value = "spend_time")
    Long spendTime;
    @TableField(value = "create_time")
    Date createTime;
}
