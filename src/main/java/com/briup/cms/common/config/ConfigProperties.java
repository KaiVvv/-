package com.briup.cms.common.config;

import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 读取配置文件中的自定义配置
 * @author YuYan
 * @date 2023-12-14 09:54:22
 */
@Data
@Component
@ConfigurationProperties("briup.config")
public class ConfigProperties {

    /* OSS七牛云存储配置 */
    /* 桶名称（存储空间名称） */
    private String ossBucket = "briup-avatar-yy2";
    /* 公钥 */
    private String ossAccessKey = "Rmcja6GTqodOBYediQ_xn7B951mJOqPiifUqr5kc";
    /* 私钥 */
    private String ossSecretKey = "t6RmBH49vzEjffNljlhDDUpAKudAiYJW18ti0Rch";
    /* URL固定前缀 */
    private String ossBaseUrl = "http://s4gslnfvl.hn-bkt.clouddn.com/";
    /* 默认文件名称 */
    private String ossDefaultFileName = "noname.jpg";

    /* 加密相关配置 */
    /* 加密强度 */
    private int securityStrength = 6;
    /* 干扰因子（使用随机UUID生成） */
    private String securitySecret = UUID.randomUUID().toString();

    /* 跨域配置 */
    /* 允许跨域访问的资源 */
    private String corsMapping = "/**";
    /* 允许跨域访问的客户端源 */
    private String corsAllowedOrigins = "*";
    /* 允许跨域访问使用的HTTP请求方法 */
    private String[] corsAllowedMethods =
            {HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name()
            };

    /* 允许跨域访问携带的HTTP请求头部 */
    private String corsAllowedHeaders = "*";
    /* 是否允许携带Cookie */
    private boolean corsAllowCredentials = false;
    /* 预检请求缓存有效时长（单位：秒） */
    private long corsMaxAge = 3600;

    /* 访问权限配置 */
    /* 需要拦截的请求URL */
    private String authIncludePathPatterns = "/**";
    /* 不需要拦截的请求URL */
    private String[] authExcludePathPatterns = {
            "/login",
            "/logout",
            "/upload",
            "/auth/category/**",
            "/auth/log/**",
            "/slideshow/**"
    };

    /* 导出、下载相关配置 */
    /* 导出Excel使用的编码格式 */
    private String excelExportEncoding = "UTF-8";
    /* 导出Excel的文件类型（Office2003之前对应XLS，Office2003之后对应XLSX） */
    private ExcelTypeEnum excelExportFileType = ExcelTypeEnum.XLSX;
    /* 导出Excel时使用的Content-Type头部字段值 */
    private String excelExportContentType = "application/octet-stream;charset=UTF-8";
    /* 导出Excel时使用的Content-Disposition头部字段值 */
    private String excelExportContentDisposition = "attachment;filename=%s";

    /* 其他配置 */
    /* 默认时间格式（解析请求参数时使用） */
    private String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
    /* IP查询服务地址 */
    private String ipQueryServerHost = "http://whois.pconline.com.cn/ipJson.jsp";


}
