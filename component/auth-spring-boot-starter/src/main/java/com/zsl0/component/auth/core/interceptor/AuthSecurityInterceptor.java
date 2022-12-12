package com.zsl0.component.auth.core.interceptor;

import com.zsl0.component.auth.core.annotation.Permissions;
import com.zsl0.component.auth.core.exception.authentication.NotAuthenticationException;
import com.zsl0.component.auth.core.exception.token.TokenExpireException;
import com.zsl0.component.auth.core.model.AuthInfo;
import com.zsl0.component.auth.core.util.SecurityContextHolder;
import com.zsl0.component.auth.core.annotation.RequireAuthentication;
import com.zsl0.component.auth.core.exception.authentication.NotAuthorizationException;
import com.zsl0.component.auth.core.model.PermissionProvide;
import com.zsl0.component.auth.core.util.HttpUtil;
import com.zsl0.component.auth.core.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 认证安全拦截器
 *
 * @author zsl0
 * create on 2022/5/22 20:01
 * email 249269610@qq.com
 */
public class AuthSecurityInterceptor implements HandlerInterceptor {

    private PermissionProvide permissionProvide;

    public AuthSecurityInterceptor(PermissionProvide permissionProvide) {
        this.permissionProvide = permissionProvide;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取userId
        String userId = this.checkToken(request);

        // 检查认证
        this.checkAuthentication((HandlerMethod) handler, userId);
        // 检查权限
        this.checkPermissions((HandlerMethod) handler, userId);
        return true;
    }

    /**
     * 检查认证
     */
    private void checkAuthentication(HandlerMethod handler, String userId) {
        boolean hasAuthentication = handler.hasMethodAnnotation(RequireAuthentication.class);
        if (hasAuthentication && userId == null) {
            throw new NotAuthenticationException();
        }
    }

    /**
     * 检查权限
     */
    private void checkPermissions(HandlerMethod handler, String userId) {
        boolean requirePermissions = handler.hasMethodAnnotation(Permissions.class);
        if (!requirePermissions && Objects.isNull(userId)) {
            return;
        }

        Permissions methodAnnotation = handler.getMethodAnnotation(Permissions.class);
        if (Objects.isNull(methodAnnotation)) {
            return;
        }
        String permission = methodAnnotation.value();

        // 检查权限 需要自定义实现permissionService
        if (!permissionProvide.hasPermission(permission)) {
            throw new NotAuthorizationException();
        }
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


    /**
     * 检查token
     * @return uuid(用户唯一凭证)
     */
    private String checkToken(HttpServletRequest request) {
        String token = HttpUtil.authentication(request);

        // 没有token时，则代表未登陆状态
        if (Objects.isNull(token) || token.isEmpty()) {
            return null;
        }

        // 检查token是否过期
        if (JwtUtil.isExpire(token)) {
            throw new TokenExpireException("token has expired");
        }

        // 根据token获取uuid、permissions
        String userId = JwtUtil.getUuid(token);
        List<String> permissions = JwtUtil.getPermissions(token);

        // 将凭证放入全局结构体中
        SecurityContextHolder.setAuth(AuthInfo.builder()
                .uuid(userId)
                .permissions(permissions)
                .build());
        return userId;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.clear();
    }
}
