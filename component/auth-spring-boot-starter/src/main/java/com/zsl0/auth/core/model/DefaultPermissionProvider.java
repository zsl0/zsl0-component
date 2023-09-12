package com.zsl0.auth.core.model;

/**
 * 默认不需要权限
 *
 * @author zsl0
 * create on 2022/6/1 23:57
 * email 249269610@qq.com
 */
public class DefaultPermissionProvider implements PermissionProvide {

    @Override
    public boolean hasPermission(String requirePermission) {
        return true;
    }
}
