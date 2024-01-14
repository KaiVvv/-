package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.util.JwtUtil;
import com.briup.cms.common.util.ResultCode;
import com.briup.cms.common.util.SecurityUtil;
import com.briup.cms.dao.UserMapper;
import com.briup.cms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Service层实现类 - 认证模块
 * 1、完成组件的基本配置
 * 1）将当前这个类注册成为SpringIoC容器中的一个Bean
 * 步骤：在类上添加@Service注解
 * 建议先将所有的Service上都添加该注解，防止项目启动失败。
 * 2）需要在当前类中引入用户模块Dao层对象（MyBatis映射接口实例）
 * 帮我们完成用户表的增删改查功能。
 * 步骤：在类中定义一个UserMapper类型的属性（全局变量），使用final修饰
 * 在当前类上添加@RequiredArgsConstructor注解，为其自动生成构造器
 * Spring构造当前类实例的时候就会自动调用该构造器将依赖注入进来
 * @author YuYan
 * @date 2024-01-09 19:43:09
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * 引入用户模块Dao层对象
     */
    private final UserMapper userMapper;
    /**
     * 引入Token工具
     */
    private final JwtUtil jwtUtil;
    /**
     * 加密工具
     */
    private final SecurityUtil securityUtil;

    /**
     * 用户登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        /* 根据用户提交的账号，查询用户信息 */
        // 创建一个查询条件模型对象，用来封装查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名字段必须等于传入的username
        lqw.eq(User::getUsername, username);
        // 执行查询（根据用户名查询最多只可能查到一条数据，所以使用selectOne()方法）
        User user = userMapper.selectOne(lqw);

        /* 根据查询的结果，判断登录的结果 */
        // 如果账号不存在，则登录失败，抛出异常
        if (user == null) {
            throw new CmsException(ResultCode.USER_USERNAME_NOT_EXIST);
        }
        // 如果密码不正确，则登录失败，抛出异常
        // password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!securityUtil.bcryptMatches(password, user.getPassword())) {
            throw new CmsException(ResultCode.USER_PASSWORD_INVALID);
        }
        // 如果账号状态不可用，则登录失败，抛出异常
        if (!user.getStatus().equals("启用")) {
            throw new CmsException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }

        /* 额外步骤：把用户信息封装起来作为载荷生成Token令牌 */
        // 创建一个Map集合，保存用户信息（载荷）
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("username", user.getUsername());
        // 调用Token工具类生成令牌字符串
        return jwtUtil.generate(map);
    }

    /**
     * 获取用户信息
     * @param token Token令牌
     * @return
     */
    @Override
    public UserExt getUserinfo(String token) {
        // 调用Token工具解析Token令牌字符串并且获取到Token中包含的载荷信息
        Map<String, Object> claims = jwtUtil.getClaims(token);
        // 从载荷中取出用户的id
        Long userId = (Long) claims.get("userId");
        // 根据用户id查询出用户的信息
        User user = userMapper.selectById(userId);
        // 把查询出来的用户对象（Entity类型）转换成实体扩展类型（Ext类型）
        return UserExt.toExt(user);
    }
}
