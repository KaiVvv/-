package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Slideshow;
import com.briup.cms.common.model.ext.SlideshowExt;

import java.util.List;

/**
 * 业务逻辑接口 - 轮播图相关
 * @author YuYan
 * @date 2023-11-29 11:45:00
 */
public interface SlideshowService {

    // 根据状态查询轮播图信息
    List<SlideshowExt> listByStatus(String status);

    // 分页+条件检索轮播图信息
    IPage<SlideshowExt> pageQueryByClause(SlideshowExt slideshowExt,
                                          IPage<Slideshow> page);

    // 根据id查询轮播图信息
    SlideshowExt getById(Integer id);

    // 新增或修改轮播图信息
    void saveOrUpdate(SlideshowExt slideshowExt);

    // 根据id删除轮播图信息
    void deleteById(Integer id);

    // 批量删除轮播图信息
    void delete(List<Integer> ids);

}
