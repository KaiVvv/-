package com.briup.cms.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YuYan
 * @date 2023-11-29 10:55:16
 */
@Data
@TableName("cms_slideshow")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Slideshow implements Serializable {

    /* 声明id是主键列，且自动增长 */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String description;
    @TableField("url")
    private String url;
    private String status;
    /* TableLogic表示该字段是逻辑删除标志字段 */
    /* 默认0表示未删除，1表示已删除 */
    private Integer deleted;
    private Date uploadTime;

}
