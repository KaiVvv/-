package com.briup.cms.service;

import com.briup.cms.common.model.ext.UserExt;

/**
 * 业务逻辑接口 - 认证相关
 * @author YuYan
 * @date 2023-12-01 14:22:46
 */
public interface AuthService {
    /**
     * 用户登录功能
     * @param username 账号
     * @param password 密码
     * @return 如果登录成功则返回Token令牌
     */
    String login(String username, String password);

    /**
     * 根据Token获取用户信息
     * @param token Token令牌
     * @return
     */
    UserExt getUserinfo(String token);

}
