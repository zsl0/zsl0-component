package com.zsl.custombox.authentication.service.user;

import com.zsl.custombox.authentication.model.pojo.login.Menu;
import com.zsl.custombox.authentication.model.pojo.login.MenuNode;

import java.util.List;

/**
 * @Author zsl
 * @Date 2022/6/9 17:38
 * @Email 249269610@qq.com
 */
public interface MenuService {

    /**
     * 根据用户id获取菜单
     */
    List<MenuNode> loadUserMenu(Long userId);

    /**
     * 获取所有菜单
     */
    List<MenuNode> loadAll();

    /**
     * 根据角色获取菜单
     */
    List<MenuNode> loadRoleMenu(Long roleId);

    /**
     * 获取单个菜单信息
     */
    Menu loadMenu(Long menuId);

    /**
     * 添加菜单
     * @return 1,执行成功
     */
    int add(Menu menu);

    /**
     * 删除菜单
     * @return 1,执行成功
     */
    int remove(Long menuId);

    /**
     * 更新菜单信息
     * @return 1,执行成功
     */
    int update(Menu menu);
}
