package com.briup.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.cms.common.model.entity.Comment;
import com.briup.cms.common.model.ext.CommentExt;
import com.briup.cms.common.model.ext.SubCommentExt;
import com.briup.cms.common.model.param.CommentDeleteParam;
import com.briup.cms.common.model.vo.CommentVO;
import com.briup.cms.common.model.vo.SubCommentVO;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 评论相关功能
 *
 * @author YuYan
 * @date 2023-12-06 11:44:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/comment")
public class CommentController {

    /**
     * 评论模块Service层对象
     */
    private final CommentService commentService;

    /**
     * 新增一级评论信息
     * @param commentVO
     * @return
     */
    @PostMapping(params = "type=parent")
    public Result saveParent(@RequestBody CommentVO commentVO) {
        commentService.save(CommentExt.toExt(commentVO));
        return Result.ok();
    }

    /**
     * 新增二级评论信息
     * @param subCommentVO
     * @return
     */
    @PostMapping(params = "type=child")
    public Result saveChild(@RequestBody SubCommentVO subCommentVO) {
        commentService.save(SubCommentExt.toExt(subCommentVO));
        return Result.ok();
    }

    /**
     * 删除评论信息
     * @param id
     * @param type
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Long id,
                         @RequestParam(value = "type") String type) {
        commentService.delete(id, type);
        return Result.ok();
    }

    /**
     * 批量删除评论信息
     * @param param
     * @return
     */
    @DeleteMapping
    public Result deleteBatch(@RequestBody List<CommentDeleteParam> param) {
        commentService.deleteBatch(param);
        return Result.ok();
    }

    /**
     * 查询某个一级评论下的所有二级评论
     * @param parentId
     * @return
     */
    @GetMapping(value = "/{id}/subcomment")
    public Result list(@PathVariable(value = "id") Long parentId) {
        /* 封装参数 */
        SubCommentExt subCommentParam = SubCommentExt.builder()
                .parentId(parentId)
                .build();
        /* 传入Service执行查询 */
        return Result.ok(SubCommentVO.toVO(
                commentService.list(subCommentParam)));
    }

    /**
     * 分页+条件检索一级评论信息
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param articleId 文章ID
     * @param content 评论内容关键字
     * @param userId 用户ID
     * @param startTime 查询的开始时间
     * @param endTime 查询的结束时间
     * @return
     */
    @GetMapping(params = "page=true")
    public Result pageQuery(@RequestParam(value = "pageNum", required = true) int pageNum,
                            @RequestParam(value = "pageSize", required = true) int pageSize,
                            @RequestParam(value = "articleId", required = false) Long articleId,
                            @RequestParam(value = "keyword", required = false) String content,
                            @RequestParam(value = "userId", required = false) Long userId,
                            @RequestParam(value = "startTime", required = false) Date startTime,
                            @RequestParam(value = "endTime", required = false) Date endTime) {
        /* 封装分页参数 */
        IPage<Comment> page = new Page<>(pageNum, pageSize);
        /* 封装查询条件 */
        CommentExt commentParam = CommentExt.builder()
                .articleId(articleId)
                .content(content)
                .userId(userId)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        /* 调用Service执行查询 */
        return Result.ok(PageUtil.convert(
                commentService.pageQueryByClause(page, commentParam),
                CommentVO::toVO));

    }


}
