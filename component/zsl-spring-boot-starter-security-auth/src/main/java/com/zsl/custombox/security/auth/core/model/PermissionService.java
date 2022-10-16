package com.zsl.custombox.security.auth.core.model;

/**
 * @Author zsl
 * @Date 2022/6/1 23:36
 * @Email 249269610@qq.com
 */
public interface PermissionService {

    /**
     * 根据权限查询角色
     *
     * @param requirePermission 需要拥有的权限
     */
    boolean hasPermission(String requirePermission);
}
