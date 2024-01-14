package com.briup.cms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.briup.cms.common.model.entity.Category;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * @author YuYan
 * @date 2023-12-04 14:03:24
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询当前表中orderNum的最大值
     * @return
     */
    @Select("select max(order_num) from cms_category")
    @ResultType(Integer.class)
    Integer selectMaxOrderNum();

}
