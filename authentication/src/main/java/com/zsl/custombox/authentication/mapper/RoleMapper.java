package com.zsl.custombox.authentication.mapper;

import com.zsl.custombox.authentication.model.pojo.login.Role;

import java.util.List;

/**
 * 角色Mapper
 *
 * @Author zsl
 * @Date 2022/6/9 14:45
 * @Email 249269610@qq.com
 */
public interface RoleMapper {

    /**
     * 查询所有
     */
    List<Role> queryAll();


    /**
     * 根据id查询
     */
    Role queryById(Long id);

    /**
     * 根据menuId查询
     */
    List<Role> queryByMenuId(Long menuId);

    /**
     * 根据userId查询
     */
    List<Role> queryByUserId(Long userId);

    /**
     * 插入
     */
    void insert(Role role);

    /**
     * 更新
     */
    void update(Role role);

    /**
     * 删除
     */
    void delete(Long id);
}
