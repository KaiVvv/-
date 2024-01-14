package com.briup.cms.common.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.model.entity.Category;
import com.briup.cms.common.util.ObjectUtil;
import com.briup.cms.common.util.ResultCode;
import com.briup.cms.dao.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author YuYan
 * @date 2023-12-12 09:30:55
 */
@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<Integer> {

    private final CategoryMapper categoryMapper;

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        /* 获取到当前读到的、需要转换的单元格数据所对应的实体属性的镜像 */
        Field field = contentProperty.getField();
        /* 读出属性的名称 */
        String fieldName = field.getName();
        /* 取出Excel单元格中的数据 */
        String value = cellData.getStringValue();
        /* 根据属性名称判断应该做何种转换 */
        if (ObjectUtil.equals(fieldName, "deleted")) {
            if (ObjectUtil.equals(value, "未删除")) {
                return 0;
            } else if (ObjectUtil.equals(value, "已删除")) {
                return 1;
            } else {
                throw new CmsException(ResultCode.CATEGORY_EXCEL_CONTENT_ERROR);
            }
        } else if (ObjectUtil.equals(fieldName, "parentId")) {
            /* 判断表格中该行数据是否定义了父栏目名称 */
            if (ObjectUtil.notHasText(value)) {
                return null;
            }
            /* 根据上传的栏目名称查询栏目的id，封装给实体Integer类型的parentId */
            LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Category::getName, value);
            Category category = categoryMapper.selectOne(lqw);
            if (ObjectUtil.isNull(category)) {
                throw new CmsException(ResultCode.PARENT_CATEGORY_IS_INVALID);
            }
            return category.getId();
        }
        return null;
    }
}
