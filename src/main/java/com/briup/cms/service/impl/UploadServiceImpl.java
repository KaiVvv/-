package com.briup.cms.service.impl;

import com.briup.cms.common.util.QiniuUtil;
import com.briup.cms.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author YuYan
 * @date 2024-01-09 19:42:44
 */
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final QiniuUtil qiniuUtil;

    @Override
    public String upload(MultipartFile multipartFile) {
        // 只需要调用写好的工具类中的方法即可
        return qiniuUtil.upload(multipartFile);
    }
}
