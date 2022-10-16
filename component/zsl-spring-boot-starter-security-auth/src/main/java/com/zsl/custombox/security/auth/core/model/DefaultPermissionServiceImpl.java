package com.zsl.custombox.security.auth.core.model;

/**
 * 默认不需要权限
 *
 * @Author zsl
 * @Date 2022/6/1 23:57
 * @Email 249269610@qq.com
 */
public class DefaultPermissionServiceImpl implements PermissionService {

    @Override
    public boolean hasPermission(String requirePermission) {
        return true;
    }
}
