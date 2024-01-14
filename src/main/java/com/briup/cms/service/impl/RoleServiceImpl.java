package com.briup.cms.service.impl;

import com.briup.cms.common.model.ext.RoleExt;
import com.briup.cms.dao.RoleMapper;
import com.briup.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务逻辑层实现类 - 角色相关
 * @author YuYan
 * @date 2024-01-09 19:42:53
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    /**
     * 角色模块Dao层对象
     */
    private final RoleMapper roleMapper;

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    @Override
    public RoleExt getById(Integer id) {
        /*
         * 1、调用MyBatisPlus框架中已经实现好的根据主键id查询数据的方法
         * 2、查到的结果是Entity类型，转换为Ext扩展类型
         * 3、返回结果
         */
        return RoleExt.toExt(roleMapper.selectById(id));
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<RoleExt> list() {
        /*
         * 1、调用selectList()方法，该方法用于根据给定的条件批量查询数据
         * 如果传入null值表示查询全部数据
         * 2、查到的结果是一个List集合，集合中的每个对象都是Entity实体类型
         * 需要转换成Ext类型
         * 3、返回结果
         */
        return RoleExt.toExt(roleMapper.selectList(null));
    }
}
