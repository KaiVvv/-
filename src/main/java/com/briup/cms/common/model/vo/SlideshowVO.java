package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.SlideshowExt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视图对象 - 轮播图
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlideshowVO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    @JsonProperty("status")
    private String status;
    @JsonProperty("uploadTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date uploadTime;

    public static List<SlideshowVO> toVO(List<SlideshowExt> slideshowExts) {
        return slideshowExts.stream()
                .map(SlideshowVO::toVO)
                .collect(Collectors.toList());
    }

    public static SlideshowVO toVO(SlideshowExt slideshowExt) {
        return SlideshowVO.builder()
                .id(slideshowExt.getId())
                .description(slideshowExt.getDescription())
                .url(slideshowExt.getUrl())
                .status(slideshowExt.getStatus())
                .uploadTime(slideshowExt.getUploadTime())
                .build();
    }

}
