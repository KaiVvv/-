package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Article;
import com.briup.cms.common.model.ext.ArticleExt;

import java.util.List;

/**
 * 业务逻辑接口 - 文章相关
 * @author YuYan
 * @date 2023-12-05 09:33:45
 */
public interface ArticleService {

    /**
     * 新增或修改文章信息
     * @param articleExt
     */
    void saveOrUpdate(ArticleExt articleExt);

    /**
     * 审核文章
     * @param id 文章ID
     * @param status 状态（审核通过、审核不通过）
     */
    void review(Long id, String status);

    /**
     * 删除文章信息
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据ID查询指定文章信息
     * @param id 文章ID
     * @param commentsNum 要附带的评论数量
     * @return
     */
    ArticleExt getById(Long id, int commentsNum);

    /**
     * 分页+条件检索文章信息
     * @param page 分页对象（包含分页参数pageNum、pageSize）
     * @param articleExt 检索条件
     * @return
     */
    IPage<ArticleExt> pageQueryByClause(IPage<Article> page,
                                        ArticleExt articleExt);

    /**
     * 查询所有文章信息
     * @return
     */
    List<ArticleExt> list();
}
