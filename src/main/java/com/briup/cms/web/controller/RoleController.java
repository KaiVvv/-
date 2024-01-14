package com.briup.cms.web.controller;

import com.briup.cms.common.model.vo.RoleVO;
import com.briup.cms.common.util.Result;
import com.briup.cms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色相关功能
 * @author YuYan
 * @date 2023-12-01 17:01:31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 查询所有角色信息
     * @return
     */
    @GetMapping
    public Result list() {
        return Result.ok(RoleVO.toVO(roleService.list()));
    }

}
