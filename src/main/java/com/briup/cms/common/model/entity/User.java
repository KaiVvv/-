package com.briup.cms.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author YuYan
 * @date 2023-11-30 14:54:08
 */
@Data
@TableName("cms_user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String password;
    private String avatar;
    private String gender;
    private String email;
    private String phone;
    private Date registerTime;
    private String status;
    private Date birthday;
    private Integer roleId;
    @TableField("is_vip")
    private Integer vip;
    @TableField("expires_time")
    private Date expireTime;
    private Integer deleted;
}
