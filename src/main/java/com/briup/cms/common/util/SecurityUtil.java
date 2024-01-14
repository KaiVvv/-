package com.briup.cms.common.util;

import com.briup.cms.common.config.ConfigProperties;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 加密工具类
 * @author YuYan
 * @date 2023-11-30 16:30:12
 */
@Data
@Configuration
public class SecurityUtil {

    /**
     * 自定义配置对象
     */
    private final ConfigProperties configProperties;

    /**
     * 校验给定的原文与密文是否匹配
     * @param rawText 原文
     * @param secureText 密文
     * @return
     */
    public boolean bcryptMatches(String rawText, String secureText) {
        return passwordEncoder().matches(rawText, secureText);
    }

    /**
     * 对字符串进行BCrypt加密
     * @param rawText 需要加密的字符串原文
     * @return
     */
    public String bcryptEncode(String rawText) {
        return passwordEncoder().encode(rawText);
    }

    /**
     * MD5加密方法
     */
    public String md5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 密码加密器Bean
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /* 对干扰因子进行加密 */
        SecureRandom secureRandom = new SecureRandom(
                configProperties.getSecuritySecret().getBytes());
        /* 创建BCrypt密码编译器对象 */
        return new BCryptPasswordEncoder(configProperties.getSecurityStrength(),
                secureRandom);
    }
}
