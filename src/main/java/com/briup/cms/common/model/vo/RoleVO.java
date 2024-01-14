package com.briup.cms.common.model.vo;

import com.briup.cms.common.model.ext.RoleExt;
import com.briup.cms.common.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YuYan
 * @date 2023-12-01 14:15:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    public static List<RoleVO> toVO(List<RoleExt> roleExts) {
        return roleExts.stream()
                .map(RoleVO::toVO)
                .collect(Collectors.toList());
    }
    public static RoleVO toVO(RoleExt roleExt) {
        if (ObjectUtil.isNull(roleExt)) {
            return null;
        }
        return RoleVO.builder()
                .id(roleExt.getId())
                .name(roleExt.getName())
                .description(roleExt.getDescription())
                .build();
    }

}
