package com.zsl0.auth.core.model;

/**
 * @author zsl0
 * create on 2022/6/1 23:36
 * email 249269610@qq.com
 */
public interface PermissionProvide {

    /**
     * 根据权限查询角色
     *
     * @param requirePermission 需要拥有的权限
     */
    boolean hasPermission(String requirePermission);
}
