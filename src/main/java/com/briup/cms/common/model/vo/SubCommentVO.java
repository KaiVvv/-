package com.briup.cms.common.model.vo;

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
public class SubCommentVO {

    @JsonProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "publishTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;
    @JsonProperty(value = "userId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonProperty(value = "parentId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    @JsonProperty(value = "replyId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long replyId;
    @JsonProperty(value = "author")
    private UserVO userVO;

    public static List<SubCommentVO> toVO(List<SubCommentExt> subCommentExts) {
        return ObjectUtil.isNull(subCommentExts) ? null :
                subCommentExts.stream()
                .map(SubCommentVO::toVO)
                .collect(Collectors.toList());
    }

    public static SubCommentVO toVO(SubCommentExt subCommentExt) {
        return ObjectUtil.isNull(subCommentExt) ? null :
                SubCommentVO.builder()
                        .id(subCommentExt.getId())
                        .content(subCommentExt.getContent())
                        .publishTime(subCommentExt.getPublishTime())
                        .userId(subCommentExt.getUserId())
                        .parentId(subCommentExt.getParentId())
                        .replyId(subCommentExt.getReplyId())
                        .userVO(UserVO.toVO(subCommentExt.getUserExt()))
                        .build();
    }

}
