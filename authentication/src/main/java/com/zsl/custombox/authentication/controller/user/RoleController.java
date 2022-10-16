package com.zsl.custombox.authentication.controller.user;

import com.zsl.custombox.authentication.controller.user.param.RoleAddParam;
import com.zsl.custombox.authentication.controller.user.param.RoleModifyParam;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import com.zsl.custombox.security.auth.core.annotation.Permissions;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zsl
 * @Date 2022/6/15 10:23
 * @Email 249269610@qq.com
 */
@RestController
@RequestMapping("/user/role")
public class RoleController {

    /**
     * 查询所有角色（获取对应菜单权限）
     */
    @GetMapping
    @Permissions("user:role:queryAll")
    public void roles() {

    }

    /**
     * 查询单个角色（获取对应菜单权限）
     */
    @GetMapping("{id}")
    @Permissions("user:role:queryById")
    public void role(@PathVariable(value = "id") Long roleId) {

    }

    /**
     * 添加角色（绑定菜单权限）, 创建RoleAddParam
     */
    @PostMapping
    @Permissions("user:role:add")
    public void addRole(RoleAddParam roleAddParam) {

    }

    /**
     * 删除角色（删除绑定权限）
     */
    @DeleteMapping("{id}")
    @Permissions("user:role:delete")
    public void removeRole(@PathVariable("id") Long roleId) {

    }

    /**
     * 修改角色（先删除权限，再绑定权限）, 创建RoleModifyParam
     */
    @PutMapping
    @Permissions("user:role:update")
    public void modifyRole(RoleModifyParam roleModifyParam) {

    }
}
