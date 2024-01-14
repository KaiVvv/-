package com.briup.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.cms.common.log.LogAccess;
import com.briup.cms.common.model.entity.Article;
import com.briup.cms.common.model.ext.ArticleExt;
import com.briup.cms.common.model.vo.ArticleVO;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 资讯相关功能
 * @author YuYan
 * @date 2023-12-05 11:29:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/article")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 查询所有资讯信息
     * @return
     */
    @GetMapping
    public Result list() {
        return Result.ok(ArticleVO.toVO(articleService.list()));
    }

    /**
     * 分页+条件检索资讯信息
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param title 标题关键字
     * @param categoryId 所属栏目ID
     * @param status 审核状态
     * @param userId 发布者ID
     * @param charged 收费状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping(params = "page=true")
    public Result pageQuery(@RequestParam(value = "pageNum", required = true) int pageNum,
                            @RequestParam(value = "pageSize", required = true) int pageSize,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "categoryId", required = false) Integer categoryId,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "userId", required = false) Long userId,
                            @RequestParam(value = "charged", required = false) Integer charged,
                            @RequestParam(value = "startTime", required = false) Date startTime,
                            @RequestParam(value = "endTime", required = false) Date endTime) {
        /* 封装分页参数 */
        IPage<Article> page = new Page<>(pageNum, pageSize);
        /* 封装查询条件 */
        ArticleExt articleExt = ArticleExt.builder()
                .title(title)
                .categoryId(categoryId)
                .status(status)
                .userId(userId)
                .charged(charged)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        return Result.ok(PageUtil.convert(
                articleService.pageQueryByClause(page, articleExt),
                ArticleVO::toVO));
    }

    /**
     * 新增或修改资讯信息
     * @param articleVO
     * @return
     */
    @PostMapping
    @LogAccess("新增或修改资讯信息")
    public Result save(@RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdate(ArticleExt.toExt(articleVO));
        return Result.ok();
    }

    /**
     * 审核资讯
     * @param id 文章ID
     * @param status 审核状态值（审核通过、审核不通过）
     * @return
     */
    @PutMapping("/{id}")
    @LogAccess("审核资讯")
    public Result review(@PathVariable(value = "id") Long id,
                         @RequestBody(required = false) String status) {
        articleService.review(id, status);
        return Result.ok();
    }

    /**
     * 删除资讯信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @LogAccess("删除资讯信息")
    public Result delete(@PathVariable(value = "ids") List<Long> ids) {
        articleService.delete(ids);
        return Result.ok();
    }

    /**
     * 根据ID查询资讯信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Long id) {
        return Result.ok(ArticleVO.toVO(articleService.getById(id, 3)));
    }

}
