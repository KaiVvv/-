package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Comment;
import com.briup.cms.common.model.entity.SubComment;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.CommentExt;
import com.briup.cms.common.model.ext.SubCommentExt;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.model.param.CommentDeleteParam;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.dao.CommentMapper;
import com.briup.cms.dao.SubCommentMapper;
import com.briup.cms.dao.UserMapper;
import com.briup.cms.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 业务逻辑层实现类 - 评论模块
 * 注意：实体划分上，一级评论和二级评论属于两种实体
 * 但是这两种实体关联性非常强，几乎不会单独进行操作。
 * 所以在组件设计上，评论相关的Controller、Service层组件都是同一个。
 * 也就是说无论操作一级评论还是二级评论，都在当前这个类中实现功能。
 * （注意，Dao层组件还是各自独立，分开实现的，因为Dao层组件是MBP框架自动提供的，
 * 是跟随着实体种类走的）
 * @author YuYan
 * @date 2024-01-09 19:42:59
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    /**
     * 一级评论模块Dao层对象
     */
    private final CommentMapper commentMapper;
    /**
     * 二级评论模块Dao层对象
     */
    private final SubCommentMapper subCommentMapper;
    /**
     * 用户模块Dao层对象
     */
    private final UserMapper userMapper;

    @Override
    public void save(CommentExt commentExt) {

    }

    @Override
    public void save(SubCommentExt commentExt) {

    }

    /**
     * 删除评论信息
     * 1）如果要删除的是二级评论，那么直接删掉即可，其他数据不受影响
     * 2）如果要删除的是一级评论，那么同时关联删除该一级评论下所有的二级评论
     * @param id
     * @param type 表示要删除的评论类型，"parent"-一级评论、"child"-二级评论
     */
    @Override
    public void delete(Long id, String type) {
        if (type.equals("parent")) {
            /* 删除一级评论 */
            // 创建一个条件模型对象，
            LambdaQueryWrapper<SubComment> lqw = new LambdaQueryWrapper<>();
            // 设置删除条件：parent_id必须等于当前要删除的一级评论的id
            lqw.eq(SubComment::getParentId, id);
            // 调用Dao层执行删除
            subCommentMapper.delete(lqw);
            // 二级评论删除完毕之后，再删除一级评论
            commentMapper.deleteById(id);
        } else if (type.equals("child")) {
            /* 删除二级评论 */
            subCommentMapper.deleteById(id);
        }
    }

    @Override
    public void deleteBatch(List<CommentDeleteParam> params) {

    }

    @Override
    public List<SubCommentExt> list(SubCommentExt subCommentParam) {
        return null;
    }

    /**
     * 分类+多条件检索评论信息
     * @param page
     * @param commentParam
     * @return
     */
    @Override
    public IPage<CommentExt> pageQueryByClause(IPage<Comment> page,
                                               CommentExt commentParam) {
        /* 取出参数 */
        Long userId = commentParam.getUserId();
        Long articleId = commentParam.getArticleId();
        String content = commentParam.getContent();
        Date startTime = commentParam.getStartTime();
        Date endTime = commentParam.getEndTime();
        /* 设置查询条件 */
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(userId != null, Comment::getUserId, userId);
        lqw.eq(articleId != null, Comment::getArticleId, articleId);
        lqw.like(StringUtils.hasText(content), Comment::getContent, content);
        // gt() great than 大于的意思   ge() greater than or equal 大于或等于
        lqw.ge(startTime != null, Comment::getPublishTime, startTime);
        // lt() less than  小于的意思   le() less than or equal  小于或等于
        lqw.le(endTime != null, Comment::getPublishTime, endTime);
        /* 调用dao层执行查询 */
        commentMapper.selectPage(page, lqw);
        /* 数据类型转换 */
        IPage<CommentExt> newPage = PageUtil.convert(page, CommentExt::toExt);

        /* 关联查询每条评论的作者信息 */
        // 获取到分页对象中包含的数据清单（一个集合对象）
        List<CommentExt> records = newPage.getRecords();
        // 遍历这个集合，为每个一级评论对象查询并封装它所属的作者信息、包含的二级评论
        for (CommentExt record : records) {
            // 从一级评论对象中取出外键作者ID（用户ID）
            Long authorId = record.getUserId();
            // 调用用户模块Dao层对象查询这个用户详细信息
            UserExt userExt = UserExt.toExt(userMapper.selectById(authorId));
            // 把查询到的用户信息封装到评论对象中（这个数据需要组合起来一并返回给前端使用）
            record.setUserExt(userExt);

            // 创建一个用于查询二级评论的条件模型对象
            LambdaQueryWrapper<SubComment> subLqw = new LambdaQueryWrapper<>();
            // 设置查询条件：parent_id等于当前一级评论对象的id
            subLqw.eq(SubComment::getParentId, record.getId());
            // 调用Dao层执行查询
            List<SubComment> subComments = subCommentMapper.selectList(subLqw);
            // 转换数据类型
            List<SubCommentExt> subCommentExts = SubCommentExt.toExt(subComments);
            // 遍历每个二级评论对象，为它查询并封装发布者信息
            for (SubCommentExt subCommentExt : subCommentExts) {
                // 从二级评论对象中取出外键作者ID（用户ID）
                Long subAuthorId = record.getUserId();
                // 调用用户模块Dao层对象查询这个用户详细信息
                userExt = UserExt.toExt(userMapper.selectById(subAuthorId));
                // 把查询到的用户信息封装到评论对象中（这个数据需要组合起来一并返回给前端使用）
                subCommentExt.setUserExt(userExt);
            }
            // 把二级评论信息封装到一级评论对象中
            record.setSubCommentExts(subCommentExts);
        }

        return newPage;
    }
}
