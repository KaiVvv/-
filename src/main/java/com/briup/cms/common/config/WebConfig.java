package com.briup.cms.common.config;

import com.briup.cms.common.util.IpUtil;
import com.briup.cms.common.util.JsonUtil;
import com.briup.cms.common.util.JwtUtil;
import com.briup.cms.common.util.StringToDateConverter;
import com.briup.cms.web.interceptor.TokenInterceptor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Web配置类
 *
 * 注册拦截器、转换器等组件，配置跨域规则
 * @author YuYan
 * @date 2023-12-01 15:01:03
 */
@Data
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /**
     * Token工具
     */
    private final JwtUtil jwtUtil;
    /**
     * Ip工具
     */
    private final IpUtil ipUtil;
    /**
     * 自定义配置对象
     */
    private final ConfigProperties configProperties;

    /**
     * 配置跨域规则
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /* 使用CorsRegistry（跨域信息注册对象）设置将来要返回的响应头部 */
        registry
                /* 配置允许跨域访问的服务器资源 */
                .addMapping(configProperties.getCorsMapping())
                /* 配置允许跨域访问资源的客户端源，响应头Access-Control-Allow-Origin字段 */
                .allowedOrigins(configProperties.getCorsAllowedOrigins())
                /* 配置允许跨域访问资源使用的请求方法，响应头Access-Control-Request-Method字段 */
                .allowedMethods(configProperties.getCorsAllowedMethods())
                /* 配置允许跨域访问时携带的头部字段，响应头Access-Control-Request-Headers字段 */
                .allowedHeaders(configProperties.getCorsAllowedHeaders())
                /* 配置是否允许跨域访问时携带Cookie信息，响应头Access-Control-Request-Credentials字段 */
                .allowCredentials(configProperties.isCorsAllowCredentials())
                /* 配置允许预检请求在浏览器端缓存的时间（单位：秒），响应头Access-Control-Request-Max-Age字段 */
                .maxAge(configProperties.getCorsMaxAge());
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                /* 添加拦截器 */
                .addInterceptor(tokenInterceptor(jwtUtil, ipUtil))
                /* 设置需要拦截的路径 */
                .addPathPatterns(configProperties.getAuthIncludePathPatterns())
                /* 设置需要排除的路径 */
                .excludePathPatterns(configProperties.getAuthExcludePathPatterns());
    }

    /**
     * 配置请求参数中日期字符串和Date对象之间的转换方式
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(defaultConvertor());
    }

    /**
     * 拦截器Bean
     * @param jwtUtil jwt工具
     * @param jsonUtil json工具
     * @return
     */
    @Bean
    public TokenInterceptor tokenInterceptor(JwtUtil jwtUtil,
                                             IpUtil jsonUtil) {
        return new TokenInterceptor(jwtUtil, jsonUtil);
    }

    /**
     * 日期格式转换器Bean
     * @return
     */
    @Bean
    public Converter<String, Date> defaultConvertor() {
        return new StringToDateConverter(
                new SimpleDateFormat(configProperties.getDefaultDatePattern()));
    }

}
