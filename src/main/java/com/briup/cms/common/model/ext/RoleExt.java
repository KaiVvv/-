package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.Role;
import com.briup.cms.common.util.BeanUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 - 扩展模型对象
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
public class RoleExt extends Role {


    public static List<RoleExt> toExt(List<Role> roles) {
        return roles.stream()
                .map(RoleExt::toExt)
                .collect(Collectors.toList());
    }

    public static RoleExt toExt(Role role) {
        return BeanUtil.copyProperties(role, RoleExt.class);
    }
}
