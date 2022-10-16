package com.zsl.custombox.authentication.service.user.impl;

import com.zsl.custombox.authentication.mapper.MenuMapper;
import com.zsl.custombox.authentication.mapper.RoleMapper;
import com.zsl.custombox.authentication.model.conveter.MenuConverter;
import com.zsl.custombox.authentication.model.pojo.login.Menu;
import com.zsl.custombox.authentication.model.pojo.login.MenuNode;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import com.zsl.custombox.authentication.service.user.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单处理服务
 *
 * @Author zsl
 * @Date 2022/6/9 17:46
 * @Email 249269610@qq.com
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<MenuNode> loadUserMenu(Long userId) {
        List<Role> roles = roleMapper.queryByUserId(userId);
        List<Menu> menus = menuMapper.queryByRoleIdes(roles.stream().map(Role::getId).collect(Collectors.toList()));

        // 去重
        List<Menu> distinct = distinct(menus);

        // 处理上下级菜单关系
        return collectMenu(distinct);
    }

    @Override
    public List<MenuNode> loadAll() {
        List<Menu> menus = menuMapper.queryAll();
        return collectMenu(menus);
    }

    @Override
    public List<MenuNode> loadRoleMenu(Long roleId) {
        List<Menu> menus = menuMapper.queryByRoleId(roleId);
        return collectMenu(menus);
    }

    @Override
    public Menu loadMenu(Long menuId) {
        return menuMapper.queryById(menuId);
    }

    @Override
    public int add(Menu menu) {
        menuMapper.insert(menu);
        return 1;
    }

    @Override
    public int remove(Long menuId) {
        menuMapper.deleteById(menuId);
        return 1;
    }

    @Override
    public int update(Menu menu) {
        menuMapper.update(menu);
        return 1;
    }

    /**
     * 去除相同id菜单
     */
    private List<Menu> distinct(List<Menu> menus) {
        Set<Menu> set = new TreeSet<>(Comparator.comparing(Menu::getId));
        set.addAll(menus);
        Iterator<Menu> iterator = set.iterator();
        List<Menu> list = new ArrayList<>(set.size());
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 处理菜单层级关系
     */
    private List<MenuNode> collectMenu(List<Menu> menus) {
        List<MenuNode> menuNodeMain = new ArrayList<>();
        List<MenuNode> menuNodes = new ArrayList<>();

        // 查找父节点
        for (Menu menu : menus) {
            MenuNode menuNode = MenuConverter.INSTANCE.menu2MenuNode(menu);
            menuNodeMain.add(menuNode);
            if (menu.getParentId() == 0L) {
                menuNodes.add(menuNode);
            }
        }

        for (MenuNode menuNode : menuNodes) {
            menuNode.setChildren(findMenuChildren(menuNode.getMenuId(), menuNodes));
        }

        return menuNodeMain;
    }

    /**
     * 查找下级菜单
     */
    private List<MenuNode> findMenuChildren(Long parentId, List<MenuNode> menuNodes) {
        List<MenuNode> menuNodeChildren = new ArrayList<>();
        for (MenuNode menuNode : menuNodes) {
            // 判断是否归属
            if (menuNode.getParentId().equals(parentId)) {
                menuNodeChildren.add(menuNode);
                menuNode.setChildren(findMenuChildren(menuNode.getMenuId(), menuNodes));
            }
        }

        if (menuNodeChildren.size() == 0) {
            return null;
        }
        return menuNodeChildren;
    }
}
