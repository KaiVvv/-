package com.briup.cms.common.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 实体类 - 栏目
 * @author YuYan
 * @date 2023-12-01 11:42:07
 */
@Data
@TableName("cms_category")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "name")
    @ExcelProperty(value = "栏目名称")
    private String name;
    @TableField(value = "description")
    @ExcelProperty(value = "栏目描述")
    private String description;
    @ExcelProperty(value = "栏目序号")
    @TableField(value = "order_num")
    private Integer orderNum;
    @TableField(value = "parent_id")
    @ExcelProperty(value = "父栏目")
    private Integer parentId;
    @TableField(value = "deleted")
    @ExcelProperty(value = "栏目删除状态")
    private Integer deleted;
}
