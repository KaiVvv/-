package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.ArticleExt;
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
 * 视图对象 - 资讯
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO {

    @JsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "status")
    private String status;
    @JsonProperty(value = "readNum")
    private Integer readNum;
    @JsonProperty(value = "likeNum")
    private Integer likeNum;
    @JsonProperty(value = "dislikeNum")
    private Integer dislikeNum;
    @JsonProperty(value = "userId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonProperty(value = "categoryId")
    private Integer categoryId;
    @JsonProperty(value = "charged")
    private Integer charged;
    @JsonProperty(value = "publishTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;
    // private List<CommentVO> comment;
    @JsonProperty(value = "author")
    private UserVO userVO;
    @JsonProperty(value = "comments")
    private List<CommentVO> commentVOS;

    public static List<ArticleVO> toVO(List<ArticleExt> articleExts) {
        return ObjectUtil.isNull(articleExts) ? null :
                articleExts.stream()
                .map(ArticleVO::toVO)
                .collect(Collectors.toList());
    }

    public static ArticleVO toVO(ArticleExt articleExt) {

        ArticleVO articleVO = ObjectUtil.isNull(articleExt) ? null :
                ArticleVO.builder()
                        .id(articleExt.getId())
                        .title(articleExt.getTitle())
                        .content(articleExt.getContent())
                        .status(articleExt.getStatus())
                        .readNum(articleExt.getReadNum())
                        .likeNum(articleExt.getLikeNum())
                        .dislikeNum(articleExt.getDislikeNum())
                        .userId(articleExt.getUserId())
                        .categoryId(articleExt.getCategoryId())
                        .charged(articleExt.getCharged())
                        .publishTime(articleExt.getPublishTime())
                        .userVO(UserVO.toVO(articleExt.getUserExt()))
                        .commentVOS(CommentVO.toVO(articleExt.getCommentExts()))
                        .build();

        return articleVO;
    }

}
