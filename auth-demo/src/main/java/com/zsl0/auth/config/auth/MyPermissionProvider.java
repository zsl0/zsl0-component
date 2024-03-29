package com.zsl0.auth.config.auth;

import com.zsl0.auth.core.model.PermissionProvide;
import com.zsl0.auth.core.util.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zsl0
 * created on 2022/12/12 22:47
 */
@Component
public class MyPermissionProvider implements PermissionProvide {
    @Override
    public boolean hasPermission(String requirePermission) {
        List<String> permissions = SecurityContextHolder.getAuth().getPermissions();
        return permissions.contains(requirePermission);
    }

    /**
     * 检查权限
     */
    private boolean checkPermission(String[] roles, List<String> requireRoles) {
        for (String requireRole : requireRoles) {
            for (String role : roles) {
                if (requireRole.equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
