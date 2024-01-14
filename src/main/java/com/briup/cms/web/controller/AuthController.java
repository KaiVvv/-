package com.briup.cms.web.controller;

import com.briup.cms.common.model.vo.UserVO;
import com.briup.cms.common.util.GlobalConstants;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证相关功能
 * @author YuYan
 * @date 2023-12-01 14:21:51
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     * @param userVO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO) {
        return Result.ok(authService.login(
                userVO.getUsername(),
                userVO.getPassword()));
    }

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/auth/userinfo")
    public Result userinfo(@RequestHeader(GlobalConstants.TOKEN_HEADER_NAME)
                                       String token) {
        return Result.ok(UserVO.toVO(authService.getUserinfo(token)));
    }

    /**
     * 用户登出
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }

}
