package com.briup.cms.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.briup.cms.common.util.GlobalConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YuYan
 * @date 2023-11-29 10:03:54
 */
@Configuration
@MapperScan(GlobalConstants.MYBATIS_MAPPER_SCAN)
public class MyBatisConfig {

    /**
     * 添加MyBatisPlus拦截器，实现分页功能
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        /* 创建一个MP总拦截器对象 */
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /* 创建一个子拦截器，参数是数据库的类型或方言（大多数情况可以不加，可以自动识别出来） */
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        /* 添加一个实现分页的子拦截器对象 */
        interceptor.addInnerInterceptor(innerInterceptor);
        /* 返回设置好的MP拦截器对象 */
        return interceptor;
    }


}
