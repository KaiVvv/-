package com.briup.cms.service;

import com.briup.cms.common.model.ext.RoleExt;

import java.util.List;

/**
 * 业务逻辑接口 - 角色相关
 * @author YuYan
 * @date 2023-12-01 11:44:49
 */
public interface RoleService {

    RoleExt getById(Integer id);

    List<RoleExt> list();

}
