package com.briup.cms.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_role")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Role {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;

}
