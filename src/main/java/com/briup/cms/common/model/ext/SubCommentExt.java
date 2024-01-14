package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.SubComment;
import com.briup.cms.common.model.vo.SubCommentVO;
import com.briup.cms.common.util.BeanUtil;
import com.briup.cms.common.util.ObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 二级评论 - 扩展模型对象
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
public class SubCommentExt extends SubComment {

    private UserExt userExt;

    public static SubCommentExt toExt(SubCommentVO subCommentVO) {
        return ObjectUtil.isNull(subCommentVO) ? null :
                SubCommentExt.builder()
                        .content(subCommentVO.getContent())
                        .userId(subCommentVO.getUserId())
                        .parentId(subCommentVO.getParentId())
                        .replyId(subCommentVO.getReplyId())
                        .build();
    }

    public static List<SubCommentExt> toExt(List<SubComment> subComments) {
        return subComments.stream()
                .map(SubCommentExt::toExt)
                .collect(Collectors.toList());
    }

    public static SubCommentExt toExt(SubComment subComment) {
        return BeanUtil.copyProperties(subComment, SubCommentExt.class);
    }

}
