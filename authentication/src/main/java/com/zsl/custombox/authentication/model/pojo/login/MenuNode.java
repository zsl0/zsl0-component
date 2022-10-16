package com.zsl.custombox.authentication.model.pojo.login;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zsl
 * @Date 2022/6/10 11:04
 * @Email 249269610@qq.com
 */
public class MenuNode {
    private Long menuId;
    private String menuName;
    private Long parentId;
    private List<MenuNode> children;

    public MenuNode() {
    }

    public MenuNode(Long menuId, String menuName) {
        this.menuId = menuId;
        this.menuName = menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    public void addChildren(MenuNode menuNode) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(menuNode);
    }
}
