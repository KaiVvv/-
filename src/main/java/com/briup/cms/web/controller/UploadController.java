package com.briup.cms.web.controller;

import com.briup.cms.common.util.Result;
import com.briup.cms.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传相关功能
 * @author YuYan
 * @date 2023-11-30 14:33:13
 */
@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    /**
     * 上传头像
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("img") MultipartFile multipartFile) {
        return Result.ok(uploadService.upload(multipartFile));
    }

}
