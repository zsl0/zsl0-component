package com.zsl.custombox.authentication.service.user;

import com.zsl.custombox.authentication.controller.user.param.RoleAddParam;
import com.zsl.custombox.authentication.controller.user.param.RoleModifyParam;
import com.zsl.custombox.authentication.controller.user.vo.RoleVo;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import com.zsl.custombox.security.auth.core.annotation.Permissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * todo 查询角色及拥有菜单权限
 *
 * @Author zsl
 * @Date 2022/6/10 17:21
 * @Email 249269610@qq.com
 */
public interface RoleService {

    /**
     * 查询所有角色（获取对应菜单权限）
     */
    List<RoleVo> roles();

    /**
     * 查询单个角色（获取对应菜单权限）
     */
    void role(Long roleId);

    /**
     * 添加角色（绑定菜单权限）, 创建RoleAddParam
     */
    void addRole(RoleAddParam roleAddParam);

    /**
     * 删除角色（删除绑定权限）
     */
    void removeRole(Long roleId);

    /**
     * 修改角色（先删除权限，再绑定权限）, 创建RoleModifyParam
     */
    void modifyRole(RoleModifyParam roleModifyParam);
}
