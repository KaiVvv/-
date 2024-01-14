package com.briup.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.briup.cms.common.exception.CmsException;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.ResultCode;
import com.briup.cms.common.util.SecurityUtil;
import com.briup.cms.dao.UserMapper;
import com.briup.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

/**
 * Service层实现类 - 用户模块
 * @author YuYan
 * @date 2024-01-09 19:42:40
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    /**
     * 用户模块Dao层对象
     */
    private final UserMapper userMapper;
    /**
     * 加密工具
     */
    private final SecurityUtil securityUtil;

    /**
     * 新增用户信息
     * @param userExt
     */
    @Override
    public void save(UserExt userExt) {
        /* 取出Ext对象中的所有参数 */
        String username = userExt.getUsername();
        String password = userExt.getPassword();
        String phone = userExt.getPhone();
        String email = userExt.getEmail();
        String gender = userExt.getGender();
        Date birthday = userExt.getBirthday();
        String avatar = userExt.getAvatar();

        /* 检查账号是否已经存在 */
        // 创建一个查询条件模型对象
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        // 设置查询条件：用户名必须等于传入的用户名参数
        lqw.eq(User::getUsername, username);
        // 调用Dao层执行查询
        User record = userMapper.selectOne(lqw);
        // 如果查到的用户不为空，则说明该用户名已被别人使用，注册失败
        if (record != null) {
            throw new CmsException(ResultCode.USER_HAS_EXISTED);
        }

        // 对用户提交的密码进行加密（MD5加密）
        // password = DigestUtils.md5DigestAsHex(password.getBytes());
        password = securityUtil.bcryptEncode(password);

        /* 把参数封装为一个Entity对象，调用Dao层实现插入 */
        User user = new User();
        /* 1）用户在界面上填写、提交的字段信息 */
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAvatar(avatar);

        /* 2）需要系统自动生成的字段信息 */
        // 设置用户的注册时间为当前时间
        user.setRegisterTime(new Date());
        // 设置用户的默认状态为启用
        user.setStatus("启用");
        // 设置用户的角色ID默认为3（普通用户）
        user.setRoleId(3);
        // 设置用户的VIP状态为0（普通用户）
        user.setVip(0);
        // 设置数据逻辑删除标志位默认为0（未删除）
        user.setDeleted(0);

        /* 把封装好的用户对象，传入Dao层执行数据的插入 */
        userMapper.insert(user);
    }

    /**
     * 删除用户信息
     * 注意：页面上删除单条数据和删除多条数据都使用本方法
     * 如果删除的是单条数据，那么传入的List集合中的元素只有一个
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        // 直接调用MBP框架中提供的批量删除方法
        userMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Override
    public UserExt getById(Long id) {
        return UserExt.toExt(userMapper.selectById(id));
    }

    /**
     * 修改用户信息
     * 注意：实现需改功能之前，需要先实现根据id查询用户信息的方法
     * update cms_user
     * set username = 'xxx',
     * phone = 'xxxx',
     * birthday = 'xxxx'
     * where xxx = xxx;
     * @param userExt
     */
    @Override
    public void update(UserExt userExt) {
        /* 取出请求参数 */
        // 前端自动提交的参数
        Long id = userExt.getId();
        // 用户在页面上填写的信息
        String avatar = userExt.getAvatar();
        String username = userExt.getUsername();
        String phone = userExt.getPhone();
        String email = userExt.getEmail();
        String gender = userExt.getGender();
        Date birthday = userExt.getBirthday();

        /* 判断要修改的名称是否和其他用户名冲突 */

        /* 把要修改的字段值封装为一个实体对象 */
        User user = new User();
        user.setAvatar(avatar);
        user.setId(id);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirthday(birthday);

        /* 调用Dao层中已经实现好的修改方法执行即可 */
        userMapper.updateById(user);
    }



    @Override
    public UserExt getByIdNullable(Long id) {
        return null;
    }


    /**
     * 分页+多条件检索用户信息
     * 分页的实现逻辑已经写好了：
     * 1）MyBatisPlus框架中提供了分页的实现逻辑
     * 2）分页的配置需要一个拦截器
     * @param userExt
     * @param page
     * @return
     */
    @Override
    public IPage<UserExt> pageQueryByClause(UserExt userExt, IPage<User> page) {
        /* 设置查询条件 */
        // 创建一个条件模型对象
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();

        // 设置查询条件：用户名（模糊匹配）
        lqw.like(StringUtils.hasText(userExt.getUsername()), User::getUsername, userExt.getUsername());
        // 设置查询条件：角色
        lqw.eq(userExt.getRoleId() != null, User::getRoleId, userExt.getRoleId());
        // 设置查询条件：VIP状态
        lqw.eq(userExt.getVip() != null, User::getVip, userExt.getVip());
        // 设置查询条件：用户状态
        lqw.eq(StringUtils.hasText(userExt.getStatus()), User::getStatus, userExt.getStatus());

        /* 执行分页查询操作，查询的结果会直接封装到这个page对象中 */
        userMapper.selectPage(page, lqw);

        /* 数据转换 */
        IPage<UserExt> newPage = PageUtil.convert(page, UserExt::toExt);

        // 其他步骤
        return newPage;
    }

    @Override
    public UserExt getByUsername(String username) {
        return null;
    }

    /**
     * 查询所有用户信息（用于资讯模块中显示用户列表供用户选择）
     * @return
     */
    @Override
    public List<UserExt> list() {
        return UserExt.toExt(userMapper.selectList(null));
    }
}
