package com.briup.cms.common.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.briup.cms.common.model.ext.CategoryExt;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 视图对象 - 栏目
 *
 * @author YuYan
 * @date 2023-11-30 09:22:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {

    @JsonProperty(value = "id")
    private Integer id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "orderNum")
    private Integer orderNum;
    @JsonProperty(value = "parentId")
    private Integer parentId;

    public static List<CategoryVO> toVO(List<CategoryExt> categoryExts) {
        return ObjectUtil.isNull(categoryExts) ? null :
                categoryExts.stream()
                .map(CategoryVO::toVO)
                .collect(Collectors.toList());
    }

    public static CategoryVO toVO(CategoryExt categoryExt) {
        return ObjectUtil.isNull(categoryExt) ? null :
                CategoryVO.builder()
                .id(categoryExt.getId())
                .name(categoryExt.getName())
                .description(categoryExt.getDescription())
                .orderNum(categoryExt.getOrderNum())
                .parentId(categoryExt.getParentId())
                .build();
    }

}
