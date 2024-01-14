package com.briup.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.cms.common.download.Download;
import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.log.LogAccess;
import com.briup.cms.common.model.entity.Category;
import com.briup.cms.common.model.ext.CategoryExt;
import com.briup.cms.common.model.vo.CategoryVO;
import com.briup.cms.common.util.*;
import com.briup.cms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 栏目相关功能
 * @author YuYan
 * @date 2023-12-04 11:08:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 新增栏目
     * @param categoryVO
     * @return
     */
    @LogAccess("新增栏目")
    @PostMapping
    public Result save(@RequestBody CategoryVO categoryVO) {
        /* 转换参数类型 */
        /* 调用Service层实现插入 */
        categoryService.save(CategoryExt.toExt(categoryVO));
        return Result.ok();
    }

    /**
     * 删除栏目信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") List<Integer> ids) {
        categoryService.delete(ids);
        return Result.ok();
    }

    /**
     * 修改栏目信息
     * @param categoryVO
     * @return
     */
    @PutMapping
    public Result update(@RequestBody CategoryVO categoryVO) {
        /* 转换参数类型 */
        /* 调用Service实现修改 */
        categoryService.update(CategoryExt.toExt(categoryVO));
        return Result.ok();
    }

    /**
     * 分页+条件检索栏目信息
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param parentId 父栏目ID
     * @return
     */
    @GetMapping(params = "page=true")
    public Result pageQuery(@RequestParam(value = "pageNum", required = true) int pageNum,
                            @RequestParam(value = "pageSize", required = true) int pageSize,
                            @RequestParam(value = "parentId", required = false) Integer parentId) {
        /* 封装参数 */
        IPage<Category> page = new Page<>(pageNum, pageSize);
        CategoryExt categoryExt = CategoryExt.builder()
                .parentId(parentId)
                .build();
        /* 调用Service实现查询 */
        return Result.ok(PageUtil.convert(
                categoryService.pageQueryByClause(page, categoryExt),
                CategoryVO::toVO));
    }

    /**
     * 根据ID查询栏目信息
     * @param id 栏目ID
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable(value = "id") Integer id,
                          @RequestParam(value = "cascadeChildren", required = false, defaultValue = "false") boolean cascadeChildren) {
        return Result.ok(CategoryVO.toVO(categoryService.getById(id, cascadeChildren)));
    }

    /**
     * 查询栏目信息
     * @param cascadeChildren 是否要关联查询二级栏目
     * @return
     */
    @GetMapping
    public Result list(@RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "cascadeChildren", required = false, defaultValue = "false") boolean cascadeChildren) {
        return Result.ok(CategoryVO.toVO(
                categoryService.list(type, cascadeChildren)));
    }

    /**
     * 用于下载导入栏目使用的Excel模板文件
     * 不再像其他接口一样返回Result格式的响应对象，
     * 而是要手动操作Response对象写回数据，所以返回值设为void即可。
     */
    @GetMapping(params = {"action=download", "type=template"})
    @Download(fileName = "栏目模板.xlsx")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        /* 获取相应输出流对象 */
        OutputStream os = response.getOutputStream();
        /* 获取读取文件的输入流对象 */
        InputStream is = ClassLoader.getSystemResourceAsStream("category_template.xlsx");
        if (ObjectUtil.isNull(is)) {
            throw new CmsException(ResultCode.CATEGORY_TEMPLATE_FILE_NOT_EXIST);
        }
        IOUtil.copyData(is, os);
    }

    /**
     * 导出栏目数据为Excel
     * @return
     * @throws Exception
     */
    @GetMapping(params = {"action=download", "type=data"})
    @Download(fileName = "栏目列表.xlsx")
    public void downloadData(HttpServletResponse response) throws Exception {
        categoryService.download(response.getOutputStream());
    }

    /**
     * 通过Excel上传栏目信息
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping(params = "action=upload")
    public Result upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        categoryService.upload(multipartFile.getInputStream());
        return Result.ok();
    }


}
