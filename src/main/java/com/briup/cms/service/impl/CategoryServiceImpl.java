package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.model.entity.Article;
import com.briup.cms.common.model.entity.Category;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.CategoryExt;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.ResultCode;
import com.briup.cms.dao.ArticleMapper;
import com.briup.cms.dao.CategoryMapper;
import com.briup.cms.dao.UserMapper;
import com.briup.cms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author YuYan
 * @date 2024-01-09 19:43:02
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    /**
     * 栏目模块Dao层对象
     */
    private final CategoryMapper categoryMapper;
    /**
     * 资讯模块的Dao层对象
     */
    private final ArticleMapper articleMapper;
    /**
     * 用户模块的Dao层对象
     */
    private final UserMapper userMapper;

    /**
     * 新增栏目信息
     * @param categoryExt
     */
    @Override
    public void save(CategoryExt categoryExt) {
        /* 取出请求参数 */
        String name = categoryExt.getName();
        String description = categoryExt.getDescription();
        Integer orderNum = categoryExt.getOrderNum();
        Integer parentId = categoryExt.getParentId();

        /* 检查栏目名称是否重复 */
        // 创建条件模型对象
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 设置查询条件：栏目名称必须等于给定的name值
        lqw.eq(Category::getName, name);
        // 执行查询，如果查到的结果不为空，则说明该名称已经被使用
        if (categoryMapper.selectOne(lqw) != null) {
            throw new CmsException(ResultCode.CATEGORY_HAS_EXISTED);
        }

        /* 将参数封装为一个Entity对象 */
        Category category = new Category();
        // 用户在界面上填写的信息
        category.setName(name);
        category.setDescription(description);
        category.setOrderNum(orderNum);
        category.setParentId(parentId);
        // 需要系统自动生成的字段
        category.setDeleted(0);

        // 调用Dao层执行插入
        categoryMapper.insert(category);
    }

    /**
     * 根据id删除栏目信息
     * 注意：
     * 1）单条数据删除和批量数据删除都使用本方法
     * 2）处理多条数据删除的过程中，如果遇到不允许删除的数据，
     * 禁止使用抛出异常的方式反馈结果，尽可能地要把所有可能删掉的数据都删掉
     * 3）对于单条数据删除，如果删除失败则必须给出提示
     *   对于多条数据删除，只要有任何一条被成功删除就算操作成功，
     *   如果所有数据都删除失败才反馈删除失败。
     *   实现：在遍历集合处理删除的过程中，记录每一次删除的结果数量
     * 栏目的删除存在特定的要求：
     * 1、如果是一级栏目，当该栏目下存在二级栏目的时候禁止删除
     * 2、如果是二级栏目，如果当前栏目下存在任何作者账号正常的资讯信息存在，则禁止删除
     * 也就是说，有以下三种情况是允许删除栏目的：
     * 1、要删除的是一级栏目，但是该一级栏目下没有二级栏目
     * 2、要删除的是二级栏目，该栏目下没有资讯信息
     * 3、要删除的是二级栏目，该栏目下有资讯信息，但是所有资讯的作者（用户）已经注销掉了
     *
     *
     * @param ids
     */
    @Override
    public void delete(List<Integer> ids) {

        // 设置一个变量，用来记录总体被实际删掉的数据条数
        int deletedCount = 0;

        // 遍历整个集合，对每个要删除的栏目进行判断处理
        for (Integer id : ids) {
            // 根据取出的id值，查出该栏目的详细信息
            Category category = categoryMapper.selectById(id);
            // 分成一级栏目和二级栏目两种方向处理
            if (category.getParentId() == null) {
                /* 要删除的是父栏目（一级栏目），判断该栏目下是否包含子栏目 */
                // 调用Dao层根据parentId查询栏目信息
                LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Category::getParentId, id);
                List<Category> categories = categoryMapper.selectList(lqw);
                if (categories == null || categories.size() == 0) {
                    // 没有子级栏目，可以删除
                    deletedCount += categoryMapper.deleteById(id);
                }
            } else {
                /* 要删除的是子栏目（二级栏目） */
                /* 查出该子栏目下的所有资讯 */
                // 创建条件模型（用来查询资讯信息）
                LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
                // 设置查询条件：所属的分类id
                lqw.eq(Article::getCategoryId, id);
                // 调用Dao层执行查询
                List<Article> articles = articleMapper.selectList(lqw);
                // 设置一个变量，用来表示最终是否允许删除
                boolean deleted = true;
                if (articles != null) {
                    for (Article article : articles) {
                        // 取出资讯的作者ID
                        Long userId = article.getUserId();
                        // 查出资讯的作者（用户）
                        User user = userMapper.selectById(userId);
                        // 判断该用户是否已经注销（逻辑删除标志位）
                        if (user.getDeleted().equals(0)) {
                            deleted = false;
                            // 只要找到一个未删除的用户信息，就可以直接结束循环了
                            break;
                        }
                    }
                }
                // 判断删除标志位，是否允许删除
                if (deleted) {
                    deletedCount += categoryMapper.deleteById(id);
                }
            }
        }

        // 所有循环执行完毕之后，判断如果被删除的数据量仍为0，则抛出异常
        if (deletedCount == 0) {
            throw new CmsException(ResultCode.CATEGORY_DELETE_FAILED);
        }
    }

    /**
     * 修改栏目信息
     * @param categoryExt
     */
    @Override
    public void update(CategoryExt categoryExt) {
        /* 取出参数 */
        Integer id = categoryExt.getId();
        String name = categoryExt.getName();
        String description = categoryExt.getDescription();
        Integer orderNum = categoryExt.getOrderNum();
        Integer parentId = categoryExt.getParentId();

        /* 检查栏目名称是否重复 */
        // 创建查询模型对象
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 设置查询条件：栏目名称
        lqw.eq(Category::getName, name);
        // 执行查询，如果查到的结果不为空则报错
        Category record = categoryMapper.selectOne(lqw);
        if (record != null && !record.getId().equals(id)) {
            throw new CmsException(ResultCode.CATEGORY_HAS_EXISTED);
        }

        /* 如果当前栏目是父栏目，则不允许改为子栏目 */
        // 调用Dao层根据id查询出这条数据的当前状态
        record = categoryMapper.selectById(id);
        // 如果该栏目本身是父栏目并且前端提交了parentId参数（表示要修改为子栏目）则报错
        if (record.getParentId() == null && parentId != null) {
            throw new CmsException(ResultCode.CATEGORY_LEVEL_SETTING_ERROR);
        }

        /* 把参数封装为一个Entity对象 */
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        category.setOrderNum(orderNum);
        category.setParentId(parentId);
        /* 调用dao层执行修改 */
        categoryMapper.updateById(category);
    }

    /**
     * 根据ID查询栏目信息
     * @param id 栏目ID
     * @param cascadeChildren
     * @return
     */
    @Override
    public CategoryExt getById(Integer id, boolean cascadeChildren) {
        // 直接调用方法根据主键查询数据
        Category category = categoryMapper.selectById(id);
        // 将Entity类型转换为Ext类型
        CategoryExt categoryExt = CategoryExt.toExt(category);

        return categoryExt;
    }

    /**
     * 根据特定的模式查询栏目信息
     * @param type 查询类型
     *             "parent"-查询一级栏目
     *             "child"-查询二级栏目
     *             null-查询全部栏目
     * @param cascadeChildren 是否要包含二级栏目
     * @return
     */
    @Override
    public List<CategoryExt> list(String type, boolean cascadeChildren) {
        // 创建查询条件模型对象
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 判断查询的类型type是查父栏目还是子栏目
        if (type.equals("parent")) {
            // 如果要查询的是父栏目，那么条件就是parent_id字段必须为空
            lqw.isNull(Category::getParentId);
        } else if (type.equals("child")) {
            // 如果要查询的是子栏目，那么条件就是parent_id字段必须非空
            lqw.isNotNull(Category::getParentId);
        }

        // 调用Dao层执行查询
        List<Category> categories = categoryMapper.selectList(lqw);

        // 转换数据类型
        List<CategoryExt> categoryExts = CategoryExt.toExt(categories);

        // TODO 根据cascadeChildren参数判断是否需要查询并封装子级数据

        return categoryExts;
    }

    /**
     *
     * @param page
     * @param categoryExt
     * @return
     */
    @Override
    public IPage<CategoryExt> pageQueryByClause(IPage<Category> page,
                                                CategoryExt categoryExt) {
        /* 取出查询条件参数 */
        Integer parentId = categoryExt.getParentId();

        /* 设置查询条件 */
        // 创建一个查询条件模型对象
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        // 设置查询条件：父栏目ID
        lqw.eq(parentId != null, Category::getParentId, parentId);

        /* 调用Dao层执行查询 */
        categoryMapper.selectPage(page, lqw);

        /* 转换数据类型，将分页对象中包含的数据清单中的每个Entity都转换为Ext类型 */
        return PageUtil.convert(page, CategoryExt::toExt);
    }

    @Override
    public void upload(InputStream is) {

    }

    @Override
    public void download(OutputStream os) {

    }
}
