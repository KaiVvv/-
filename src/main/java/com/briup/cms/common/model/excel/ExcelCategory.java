package com.briup.cms.common.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YuYan
 * @date 2023-12-11 17:27:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategory {

    @ExcelProperty(value = "栏目名称")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String name;
    @ExcelProperty(value = "栏目描述")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String description;
    @ExcelProperty(value = "栏目序号")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private Integer orderNum;
    @ExcelProperty(value = "栏目删除状态")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String deleted;
    @ExcelProperty(value = "父栏目")
    @ColumnWidth(20)
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    private String parentName;


}
