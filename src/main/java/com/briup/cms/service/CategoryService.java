package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Category;
import com.briup.cms.common.model.ext.CategoryExt;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 业务逻辑接口 - 栏目相关
 * @author YuYan
 * @date 2023-12-04 11:22:42
 */
public interface CategoryService {

    /**
     * 新增栏目信息
     * @param categoryExt
     */
    void save(CategoryExt categoryExt);

    /**
     * 删除栏目信息
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 修改栏目信息
     * @param categoryExt
     */
    void update(CategoryExt categoryExt);

    /**
     * 根据ID查询栏目信息
     * @param id 栏目ID
     * @return
     */
    CategoryExt getById(Integer id, boolean cascadeChildren);

    /**
     * 查询所有一级栏目
     * @param cascadeChildren 是否要包含二级栏目
     * @param type 查询类型
     *             "parent"-查询一级栏目
     *             "child"-查询二级栏目
     *             null-查询全部栏目
     * @return
     */
    List<CategoryExt> list(String type, boolean cascadeChildren);

    /**
     * 分页+条件检索栏目信息
     * @return
     */
    IPage<CategoryExt> pageQueryByClause(
            IPage<Category> page, CategoryExt categoryExt);

    /**
     * 上传Excel栏目信息
     * @param is
     */
    void upload(InputStream is);

    /**
     * 导出栏目数据为Excel表格
     */
    void download(OutputStream os);

}
