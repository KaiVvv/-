package com.briup.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.cms.common.log.LogAccess;
import com.briup.cms.common.model.entity.User;
import com.briup.cms.common.model.ext.UserExt;
import com.briup.cms.common.model.vo.UserVO;
import com.briup.cms.common.util.PageUtil;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关功能
 * @author YuYan
 * @date 2023-12-01 10:00:57
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserController {

    private final UserService userService;

    /**
     * 新增用户信息
     * @param userVO
     * @return
     */
    @PostMapping
    @LogAccess("新增用户信息")
    public Result save(@RequestBody UserVO userVO) {
        userService.save(UserExt.toExt(userVO));
        return Result.ok();
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Long id) {
        return Result.ok(UserVO.toVO(userService.getById(id)));
    }

    /**
     * 更新用户信息
     * @param userVO
     * @return
     */
    @PutMapping
    @LogAccess("修改用户信息")
    public Result update(@RequestBody UserVO userVO) {
        userService.update(UserExt.toExt(userVO));
        return Result.ok();
    }

    /**
     * 删除用户信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @LogAccess("删除用户信息")
    public Result delete(@PathVariable("ids") List<Long> ids) {
        userService.delete(ids);
        return Result.ok();
    }

    /**
     * 分页+条件检索用户信息
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param vip VIP状态
     * @param roleId 角色ID
     * @param status 用户状态
     * @param username 用户名
     * @return
     */
    @GetMapping(params = "page=true")
    public Result pageQuery(@RequestParam(value = "pageNum") int pageNum,
                            @RequestParam(value = "pageSize") int pageSize,
                            @RequestParam(value = "isVip", required = false) Integer vip,
                            @RequestParam(value = "roleId", required = false) Integer roleId,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "username", required = false) String username) {
        /* 封装条件参数 */
        UserExt userExt = new UserExt();
        userExt.setUsername(username);
        userExt.setVip(vip);
        userExt.setStatus(status);
        userExt.setRoleId(roleId);
        /* 封装分页参数 */
        IPage<User> page = new Page<>(pageNum, pageSize);

        return Result.ok(PageUtil.convert(
                userService.pageQueryByClause(userExt, page),
                UserVO::toVO));
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping
    public Result list() {
        return Result.ok(UserVO.toVO(userService.list()));
    }

}
