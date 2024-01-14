package com.briup.cms.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求参数String-Date转换器
 * @author YuYan
 * @date 2023-12-05 17:22:24
 */
@RequiredArgsConstructor
public class StringToDateConverter implements Converter<String, Date> {

    private final SimpleDateFormat format;

    @Override
    @SneakyThrows
    public Date convert(@NotNull String source) {
        return ObjectUtil.notHasText(source) ? null
                : format.parse(source);
    }

}
