package com.briup.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.cms.common.log.LogAccess;
import com.briup.cms.common.model.entity.Slideshow;
import com.briup.cms.common.model.ext.SlideshowExt;
import com.briup.cms.common.model.vo.SlideshowVO;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.SlideshowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图相关功能
 * @author YuYan
 * @date 2023-11-30 09:07:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/slideshow")
public class SlideshowController {

    private final SlideshowService slideshowService;

    /**
     * 根据状态查询轮播图信息
     * @return
     */
    @GetMapping
    public Result listByStatus(@RequestParam(value = "status", required = true) String status) {
        return Result.ok(SlideshowVO.toVO(
                slideshowService.listByStatus(status)));
    }

    /**
     * 分页+条件查询轮播图信息
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param description 轮播图描述
     * @param status 轮播图状态（启用，禁用）
     * @return
     */
    @GetMapping(params = "page=true")
    public Result pageQuery(@RequestParam(value = "pageNum") int pageNum,
                            @RequestParam(value = "pageSize") int pageSize,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "status", required = false) String status) {
        /* 封装条件参数 */
        SlideshowExt slideshowExt = new SlideshowExt();
        slideshowExt.setDescription(description);
        slideshowExt.setStatus(status);
        /* 封装分页参数 */
        IPage<Slideshow> page = new Page<>(pageNum, pageSize);
        return Result.ok(PageUtil.convert(
                slideshowService.pageQueryByClause(slideshowExt, page),
                SlideshowVO::toVO));
    }

    /**
     * 根据ID查询轮播图信息
     * @param id 轮播图ID
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        return Result.ok(SlideshowVO.toVO(
                slideshowService.getById(id)));
    }

    /**
     * 新增或修改轮播图信息
     * @param slideshowVO 轮播图属性
     * @return
     */
    @PostMapping
    @LogAccess("新增或修改轮播图信息")
    public Result saveOrUpdate(@RequestBody SlideshowVO slideshowVO) {
        /* 转换参数类型 VO -> Ext */
        slideshowService.saveOrUpdate(SlideshowExt.toExt(slideshowVO));
        return Result.ok();
    }

    /**
     * 删除轮播图信息
     * 可以删除一个，也可以删除多个
     * @param ids 要删除的轮播图ID
     * @return
     */
    @DeleteMapping("/{ids}")
    @LogAccess("删除轮播图信息")
    public Result delete(@PathVariable("ids") List<Integer> ids) {
        slideshowService.delete(ids);
        return Result.ok();
    }

}
