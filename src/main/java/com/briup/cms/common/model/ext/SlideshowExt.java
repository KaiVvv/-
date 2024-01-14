package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.Slideshow;
import com.briup.cms.common.model.vo.SlideshowVO;
import com.briup.cms.common.util.BeanUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 轮播图 - 扩展模型对象
 *
 * 基于Entity实体，扩展入参和出参所需要的属性及方法
 * @author YuYan
 * @date 2023-11-30 15:09:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
// @NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class SlideshowExt extends Slideshow {

    public static SlideshowExt toExt(SlideshowVO slideshowVO) {
        return SlideshowExt.builder()
                .id(slideshowVO.getId())
                .description(slideshowVO.getDescription())
                .url(slideshowVO.getUrl())
                .status(slideshowVO.getStatus())
                .build();
    }

    public static List<SlideshowExt> toExt(List<Slideshow> slideshows) {
        if (slideshows == null) {
            return null;
        }
        return slideshows.stream()
                .map(SlideshowExt::toExt)
                .collect(Collectors.toList());
    }

    public static SlideshowExt toExt(Slideshow slideshow) {
        return BeanUtil.copyProperties(slideshow, SlideshowExt.class);
    }

}
