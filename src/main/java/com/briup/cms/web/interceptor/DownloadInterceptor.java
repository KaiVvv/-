package com.briup.cms.web.interceptor;

import com.briup.cms.common.config.ConfigProperties;
import com.briup.cms.common.download.Download;
import com.briup.cms.common.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 下载拦截器
 * @author YuYan
 * @date 2023-12-15 17:27:20
 */
@Component
@RequiredArgsConstructor
public class DownloadInterceptor implements HandlerInterceptor {

    /**
     * 自定义配置对象
     */
    private final ConfigProperties configProperties;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        /* 如果不是映射到接口方法的请求则直接跳过（例如跨域预检请求） */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        /* 获取Controller中接口方法上的@Download注解 */
        Download downloadAnnotation = ((HandlerMethod) handler).getMethodAnnotation(Download.class);
        /* 如果该值为空则说明方法上没有添加该注解，无需拦截直接放行 */
        if (ObjectUtil.isNull(downloadAnnotation)) {
            return true;
        }
        /* 从注解中读出要返回的文件名称 */
        String fileName = downloadAnnotation.fileName();
        /* 设置响应头部 */
        handleResponseForExcel(response, fileName);
        return true;
    }

    /**
     * 设置下载文件响应头部
     * @param response
     * @param fileName
     */
    @SneakyThrows
    public void handleResponseForExcel(HttpServletResponse response, String fileName) {
        String encoding = StandardCharsets.UTF_8.displayName();
        /* 将文件名进行编码 */
        fileName = URLEncoder.encode(fileName, encoding);
        /* 设置响应使用的编码格式 */
        response.setCharacterEncoding(encoding);
        /* 设置响应头部Content-Type */
        response.setContentType(configProperties.getExcelExportContentType());
        /* 设置响应头部Content-Disposition（从配置文件中读出前缀，使用文件名替换占位符） */
        response.addHeader("Content-Disposition",
                String.format(configProperties.getExcelExportContentDisposition(), fileName));
    }


}
