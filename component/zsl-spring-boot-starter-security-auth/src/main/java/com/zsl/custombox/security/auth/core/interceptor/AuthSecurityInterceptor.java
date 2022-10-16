package com.zsl.custombox.security.auth.core.interceptor;

import com.zsl.custombox.common.core.exception.GlobalException;
import com.zsl.custombox.common.core.service.cache.TokenServer;
import com.zsl.custombox.common.util.HttpUtil;
import com.zsl.custombox.security.auth.core.annotation.RequireAuthentication;
import com.zsl.custombox.security.auth.core.annotation.Permissions;
import com.zsl.custombox.security.auth.core.model.DefaultUserDetails;
import com.zsl.custombox.common.util.JsonUtil;
import com.zsl.custombox.common.util.SecurityContextHolder;
import com.zsl.custombox.common.util.TokenUtil;
import com.zsl.custombox.security.auth.core.model.PermissionService;
import lombok.Setter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.zsl.custombox.common.core.http.ResponseResultStatus.UNAUTHORIZED;
import static com.zsl.custombox.common.core.http.ResponseResultStatus.FORBIDDEN;

/**
 * 认证安全拦截器
 *
 * @Author zsl
 * @Date 2022/5/22 20:01
 * @Email 249269610@qq.com
 */
@Setter
public class AuthSecurityInterceptor implements HandlerInterceptor {

    TokenServer tokenServer;

    PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取userId
        String userId = this.getUserId(request);

        // 检查认证
        this.checkAuthentication((HandlerMethod) handler, userId);
        // 检查权限
        this.checkPermissions((HandlerMethod) handler, userId);
        return true;
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
        if (!permissionService.hasPermission(permission)) throw new GlobalException(FORBIDDEN);
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
     * 检查认证
     */
    private void checkAuthentication(HandlerMethod handler, String userId) {
        boolean hasAuthentication = handler.hasMethodAnnotation(RequireAuthentication.class);
        if (hasAuthentication && userId == null) {
            throw new GlobalException(UNAUTHORIZED);
        }
    }

    private String getUserId(HttpServletRequest request) {
        String userId = null;
        String token = HttpUtil.authentication(request);
        if (Objects.nonNull(token)) {
            // 根据token获取uuid
            String uuid = TokenUtil.getAccessTokenUuid(token);

            // 解析uuid
            if (Objects.nonNull(uuid)) {
                // todo redis缓存内容是否加密
                com.zsl.custombox.common.model.authentication.Authentication authentication = JsonUtil.getJsonObject(tokenServer.get(uuid), DefaultUserDetails.class);
                userId = (authentication == null || authentication.getUserId() == null) ? null : authentication.getUserId().toString();

                // 将凭证放入全局结构体中
                SecurityContextHolder.setAuth(authentication);
            }
        }
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
