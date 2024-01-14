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
 * 实体类 - 一级评论（评论文章）
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_comment")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Comment {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "content")
    private String content;
    @TableField(value = "publish_time")
    private Date publishTime;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "article_id")
    private Long articleId;
    @TableField(value = "deleted")
    private Integer deleted;

}


