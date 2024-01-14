package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.model.entity.Article;
import com.briup.cms.common.model.ext.ArticleExt;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.RequestInfoHolder;
import com.briup.cms.dao.ArticleMapper;
import com.briup.cms.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 业务逻辑层实现类 - 资讯模块
 * @author YuYan
 * @date 2024-01-09 19:42:29
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    /**
     * 资讯模块Dao层对象
     */
    private final ArticleMapper articleMapper;

    /**
     * 新增或修改资讯信息
     * 如果参数中包含资讯的ID，则为修改操作。
     * 如果参数中不含资讯的ID，则为新增操作。
     * @param articleExt
     */
    @Override
    public void saveOrUpdate(ArticleExt articleExt) {
        /* 取出参数 */
        Long id = articleExt.getId();
        String title = articleExt.getTitle();
        Integer charged = articleExt.getCharged();
        Integer categoryId = articleExt.getCategoryId();
        String content = articleExt.getContent();

        /* 获取当前用户的ID（当前用户就是文章的作者）
         * 原理：
         * 1）用户登录成功之后，后端会给用户签发一个Token令牌
         * 通过响应，返回给浏览器端。浏览器端会把这个Token保存起来。
         * 2）前端在之后的每一次请求过程中，都携带这个Token
         * 3）后端有一个拦截器，拦截所有请求，取出请求头部中的Token，
         * 对Token字符串进行验证。如果允许通过，流程才会继续进行。
         * 判断内容：用户是否登录、用户的权限是否包含本次要调用的接口……
         * 拦截器如果验证得知Token合法，就会将请求放行。
         * 而且还会把Token中的用户信息暂时存放在一个ThreadLocal线程局部变量作用域中
         * 为了方便其他后续的模块取出和使用这些用户信息。
         *
         */
        Long userId = RequestInfoHolder.getLong("userId");

        // 把参数封装为一个Entity对象
        Article article = new Article();
        article.setId(id);
        /* 用户在页面上填写的字段 */
        article.setTitle(title);
        article.setCharged(charged);
        article.setCategoryId(categoryId);
        article.setContent(content);

        /* 判断本次操作是新增还是修改 */
        if (id != null) {
            /* 修改操作 */
            // 如果当前修改操作，是用户修改自己未通过审核的文章，那么需要将状态再次改为未审核
            // 如果当前是未审核状态，那么修改结束之后应该还是未审核状态
            // 如果当前是审核不通过状态，那么修改结束之后应该变成未审核状态
            article.setStatus("未审核");
            // 调用Dao层执行插入即可
            articleMapper.updateById(article);
            return;
        }
        /* 新增操作 */
        article.setUserId(userId);
        /* 新增文章的时候系统自动生成的字段 */
        // 资讯刚发表时都默认设为未审核状态
        article.setStatus("未审核");
        // 系统自动设置资讯的发布时间为当前时间
        article.setPublishTime(new Date());
        // 逻辑删除标志位
        article.setDeleted(0);
        // 阅读量、点赞量、点踩量都设为0
        article.setReadNum(0);
        article.setLikeNum(0);
        article.setDislikeNum(0);
        // 调用Dao层执行插入
        articleMapper.insert(article);

    }

    /**
     * 资讯审核
     * 把某个用户发布的资讯设置为审核通过或审核不通过状态
     * @param id 文章ID
     * @param status 状态（审核通过、审核不通过）
     */
    @Override
    public void review(Long id, String status) {
        // 把参数id、status封装为一个Entity对象
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        // 调用Dao层执行修改
        articleMapper.updateById(article);
    }

    @Override
    public void delete(List<Long> ids) {
        articleMapper.deleteBatchIds(ids);
    }

    /**
     * 根据ID查询资讯信息
     * @param id 文章ID
     * @param commentsNum 要附带的评论数量
     * @return
     */
    @Override
    public ArticleExt getById(Long id, int commentsNum) {
        ArticleExt articleExt = ArticleExt.toExt(articleMapper.selectById(id));

        // 查询该文章下的几条评论

        return articleExt;
    }

    /**
     * 分页+多条件检索资讯信息
     * 注意事项：
     * 1）一般字符串性质的字段，多数采用模糊匹配（关键字检索）
     * 2）有一些查询条件是关于其他实体种类信息的条件设置，
     * 例如：所属栏目、发布者（用户），需要先发送请求给后台查询数据列表，用于页面的渲染
     * 3）查询的时间是一个范围选择模式
     * @param page 分页对象（包含分页参数pageNum、pageSize）
     * @param articleExt 检索条件
     * @return
     */
    @Override
    public IPage<ArticleExt> pageQueryByClause(IPage<Article> page,
                                               ArticleExt articleExt) {
        /* 取出参数 */
        String title = articleExt.getTitle();
        Integer categoryId = articleExt.getCategoryId();
        String status = articleExt.getStatus();
        Long userId = articleExt.getUserId();
        Integer charged = articleExt.getCharged();
        Date startTime = articleExt.getStartTime();
        Date endTime = articleExt.getEndTime();

        /* 把参数封装为一个条件模型对象 */
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        // 文章标题，模糊匹配
        lqw.like(StringUtils.hasText(title), Article::getTitle, title);
        // 所属栏目，精准匹配
        lqw.eq(categoryId != null, Article::getCategoryId, categoryId);
        // 审核状态，精准匹配
        lqw.eq(StringUtils.hasText(status), Article::getStatus, status);
        // 发布者，精准匹配
        lqw.eq(userId != null, Article::getUserId, userId);
        // 收费状态，精准匹配
        lqw.eq(charged != null, Article::getCharged, charged);
        // 开始时间，范围匹配（发布时间要晚于限定的开始时间）
        // gt = great than 大于的意思
        lqw.ge(startTime != null, Article::getPublishTime, startTime);
        // 结束时间，范围匹配（发布时间要早于限定的结束时间）
        // lt = less than 小于的意思
        lqw.le(endTime != null, Article::getPublishTime, endTime);

        /* 执行分页查询 */
        articleMapper.selectPage(page, lqw);

        /* 转换数据类型 */
        IPage<ArticleExt> newPage = PageUtil.convert(page, ArticleExt::toExt);

        //...

        return newPage;
    }

    /**
     * 查询所有资讯信息（用于评论管理模块下拉列表中显示和选择文章）
     * @return
     */
    @Override
    public List<ArticleExt> list() {
        return ArticleExt.toExt(articleMapper.selectList(null));
    }
}
