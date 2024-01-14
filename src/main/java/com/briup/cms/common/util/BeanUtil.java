package com.briup.cms.common.util;

import com.briup.cms.common.exception.CmsException;
import org.springframework.beans.BeanUtils;

/**
 * 工具类 - 处理数据模型对象
 * @author YuYan
 * @date 2023-11-16 14:50:37
 */
public class BeanUtil {

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static <T> T copyProperties(Object source, Class<T> poType) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        try {
            /* 通过反射机制创建目标对象 */
            T target = poType.newInstance();
            /* 从源对象中复制属性到目标对象中 */
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException(ResultCode.SYSTEM_INNER_ERROR);
        }

    }


}
