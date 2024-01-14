package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.CommentExt;
import com.briup.cms.common.model.ext.SubCommentExt;
import com.briup.cms.common.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图对象 - 二级评论
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    @JsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "userId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonProperty(value = "articleId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;
    @JsonProperty(value = "publishTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;
    @JsonProperty(value = "author")
    private UserVO userVO;
    @JsonProperty(value = "childComments")
    private List<SubCommentVO> subCommentVOS;



    public static List<CommentVO> toVO(List<CommentExt> commentExts) {
        return ObjectUtil.isNull(commentExts) ? null :
                commentExts.stream()
                .map(CommentVO::toVO)
                .collect(Collectors.toList());
    }

    public static CommentVO toVO(CommentExt commentExt) {
        return ObjectUtil.isNull(commentExt) ? null :
                CommentVO.builder()
                        .id(commentExt.getId())
                        .content(commentExt.getContent())
                        .userId(commentExt.getUserId())
                        .articleId(commentExt.getArticleId())
                        .publishTime(commentExt.getPublishTime())
                        .userVO(UserVO.toVO(commentExt.getUserExt()))
                        .subCommentVOS(SubCommentVO.toVO(commentExt.getSubCommentExts()))
                        .build();
    }

}
