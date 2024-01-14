package com.briup.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Comment;
import com.briup.cms.common.model.ext.CommentExt;
import com.briup.cms.common.model.ext.SubCommentExt;
import com.briup.cms.common.model.param.CommentDeleteParam;

import java.util.List;

/**
 * 业务逻辑接口 - 一级评论/二级评论相关
 * @author YuYan
 * @date 2023-12-06 11:16:49
 */
public interface CommentService {

    /**
     * 新增一级评论
     * @param commentExt
     */
    void save(CommentExt commentExt);

    /**
     * 新增二级评论
     * @param commentExt
     */
    void save(SubCommentExt commentExt);

    /**
     * 删除评论
     * @param id
     * @param type
     */
    void delete(Long id, String type);

    /**
     * 批量删除评论信息
     * @param params
     */
    void deleteBatch(List<CommentDeleteParam> params);

    /**
     * 根据条件查询二级评论信息
     * @param subCommentParam
     * @return
     */
    List<SubCommentExt> list(SubCommentExt subCommentParam);

    // 文章ID
    // pageNum pageSize
    // 关键字、用户id、
    // 文章id、发表时间
    IPage<CommentExt> pageQueryByClause(IPage<Comment> page, CommentExt commentParam);
}
