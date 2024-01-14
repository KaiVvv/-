package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.Comment;
import com.briup.cms.common.model.vo.CommentVO;
import com.briup.cms.common.util.BeanUtil;
import com.briup.cms.common.util.ObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 一级评论 - 扩展模型对象
 *
 * 基于Entity实体，扩展入参和出参所需要的属性及方法
 * @author YuYan
 * @date 2023-11-30 15:09:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CommentExt extends Comment {

    private List<SubCommentExt> subCommentExts;
    private UserExt userExt;
    private Date startTime;
    private Date endTime;

    public static CommentExt toExt(CommentVO commentVO) {
        return ObjectUtil.isNull(commentVO) ? null :
                CommentExt.builder()
                        .articleId(commentVO.getArticleId())
                        .content(commentVO.getContent())
                        .userId(commentVO.getUserId())
                        .build();
    }

    public static List<CommentExt> toExt(List<Comment> comments) {
        return comments.stream()
                .map(CommentExt::toExt)
                .collect(Collectors.toList());
    }

    public static CommentExt toExt(Comment comment) {
        return BeanUtil.copyProperties(comment, CommentExt.class);
    }

}
