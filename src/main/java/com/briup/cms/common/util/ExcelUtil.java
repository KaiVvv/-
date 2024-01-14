package com.briup.cms.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.briup.cms.common.config.ConfigProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author YuYan
 * @date 2023-12-11 17:20:17
 */
@Component
@RequiredArgsConstructor
public class ExcelUtil {

    /**
     * 自定义配置对象
     */
    private final ConfigProperties configProperties;

    public <Entity> List<Entity> read(InputStream is, Class<Entity> tClass, Converter<?>...converters) {
        ExcelReaderBuilder builder = EasyExcel.read(is)
                .head(tClass);
        /* 遍历数组，注册所有的转换器 */
        if (converters != null && converters.length != 0) {
            for (Converter<?> converter : converters) {
                builder.registerConverter(converter);
            }
        }
        return builder.doReadAllSync();
    }

    public <Entity> void write(OutputStream os, Class<Entity> tClass, List<Entity> dataList, Converter<?>...converters) {
        /* 获取到输出器的建造者对象，用来指定输出的相关规则属性 */
        ExcelWriterBuilder builder = EasyExcel.write();
        /* 指定要输出的文件 */
        ExcelWriterBuilder writerBuilder = builder.file(os)
                /* 指定与表格对应的实体类 */
                .head(tClass)
                /* 指定要输出的文件类型 */
                .excelType(configProperties.getExcelExportFileType());

        /* 遍历数组，注册所有的转换器 */
        if (converters != null && converters.length != 0) {
            for (Converter<?> converter : converters) {
                writerBuilder.registerConverter(converter);
            }
        }
        /* 调用write()方法，写入数据即可 */
        WriteSheet sheet = EasyExcel
                .writerSheet()
                .build();
        ExcelWriter writer = writerBuilder.build();
        writer.write(dataList, sheet);
        writer.finish();
        writer.close();
    }

}
