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
 * 实体类 - 二级评论（回复评论）
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_subcomment")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SubComment {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "content")
    private String content;
    @TableField(value = "publish_time")
    private Date publishTime;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "parent_id")
    private Long parentId ;
    @TableField(value = "reply_id")
    private Long replyId;
    @TableField(value = "deleted")
    private Integer deleted;

}


