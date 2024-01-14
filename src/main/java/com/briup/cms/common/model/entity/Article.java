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
 * 实体类 - 文章
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_article")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Article {
    // 主键ID
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    //
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @TableField(value = "status")
    private String status;
    @TableField(value = "read_num")
    private Integer readNum;
    @TableField(value = "like_num")
    private Integer likeNum;
    @TableField(value = "dislike_num")
    private Integer dislikeNum;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "category_id")
    private Integer categoryId;
    @TableField(value = "charged")
    private Integer charged;
    @TableField(value = "publish_time")
    private Date publishTime;
    @TableField(value = "deleted")
    private Integer deleted;
}


