package com.briup.cms.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 业务逻辑接口 - 文件上传相关
 * @author YuYan
 * @date 2023-11-30 14:35:22
 */
public interface UploadService {

    /**
     * 上传文件
     * @param multipartFile 文件对象
     * @return OSS上完整的文件访问URL
     */
    String upload(MultipartFile multipartFile);

}
