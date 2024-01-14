package com.briup.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;


/* 排除掉SpringSecurity自动配置类，防止认证拦截器被注册到IoC容器中 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAsync
public class BriupCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BriupCmsApplication.class, args);
    }

}
