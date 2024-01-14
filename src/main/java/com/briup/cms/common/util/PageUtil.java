package com.briup.cms.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author YuYan
 * @date 2023-11-29 14:46:19
 */
public class PageUtil {
    /**
     *
     * @param source 包含原对象类型的分页对象
     * @param function 用来转换对象类型的方法
     * @param <T> 原对象类型
     * @param <R> 目标对象类型
     * @return 包含目标对象类型的分页对象
     */
    public static <T, R> IPage<R> convert(
            IPage<T> source, Function<T, R> function) {
        /* 创建一个新的分页对象 */
        IPage<R> target = new Page<>();
        /* 将传入的Page对象的属性复制到新Page对象中 */
        BeanUtils.copyProperties(source, target);
        /* 将传入的Page对象中的数据集合中的每个元素都转换成目标类型 */
        List<R> newList = source.getRecords()
                .stream()
                .map(function)
                .collect(Collectors.toList());
        /* 将转换好类型的集合设置到新的分页对象中 */
        target.setRecords(newList);
        return target;
    }

}
