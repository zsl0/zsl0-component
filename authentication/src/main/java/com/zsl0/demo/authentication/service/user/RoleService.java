package com.zsl0.demo.authentication.service.user;

import com.zsl0.demo.authentication.controller.user.param.RoleAddParam;
import com.zsl0.demo.authentication.controller.user.param.RoleModifyParam;
import com.zsl0.demo.authentication.controller.user.vo.RoleVo;

import java.util.List;

/**
 * todo 查询角色及拥有菜单权限
 *
 * @author zsl0
 * create on 2022/6/10 17:21
 * email 249269610@qq.com
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
