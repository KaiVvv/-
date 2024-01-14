package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.UserExt;

import java.util.List;

/**
 * 业务逻辑接口 - 用户相关
 * @author YuYan
 * @date 2023-11-30 15:12:58
 */
public interface UserService {

    /**
     * 新增用户信息
     * @param userExt
     */
    void save(UserExt userExt);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    UserExt getById(Long id);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    UserExt getByIdNullable(Long id);

    /**
     * 更新用户信息
     * @param userExt
     */
    void update(UserExt userExt);

    /**
     * 删除用户信息
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 分页+条件检索用户信息
     * @param userExt
     * @return
     */
    IPage<UserExt> pageQueryByClause(UserExt userExt, IPage<User> page);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return
     */
    UserExt getByUsername(String username);

    /**
     * 查询所有用户信息
     * @return
     */
    List<UserExt> list();
}
