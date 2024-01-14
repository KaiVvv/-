package com.briup.cms.common.model.ext;

import com.briup.cms.common.model.entity.Article;
import com.briup.cms.common.model.vo.ArticleVO;
import com.briup.cms.common.util.BeanUtil;
import com.briup.cms.common.util.ObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资讯 - 扩展模型对象
 *
 * 基于Entity实体，扩展入参和出参所需要的属性及方法
 * @author YuYan
 * @date 2023-11-30 15:09:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ArticleExt extends Article {

    /**
     * 查询的开始时间
     */
    private Date startTime;
    /**
     * 查询结束时间
     */
    private Date endTime;
    /**
     * 文章作者
     */
    private UserExt userExt;
    /**
     * 文章包含的部分评论
     */
    private List<CommentExt> commentExts;

    public static ArticleExt toExt(ArticleVO articleVO) {

        return ObjectUtil.isNull(articleVO) ? null :
                ArticleExt.builder()
                .id(articleVO.getId())
                .title(articleVO.getTitle())
                .content(articleVO.getContent())
                .categoryId(articleVO.getCategoryId())
                .userId(articleVO.getUserId())
                .charged(articleVO.getCharged())
                .build();
    }

    public static List<ArticleExt> toExt(List<Article> articles) {
        return articles.stream()
                .map(ArticleExt::toExt)
                .collect(Collectors.toList());
    }

    public static ArticleExt toExt(Article article) {
        return BeanUtil.copyProperties(article, ArticleExt.class);
    }
}
