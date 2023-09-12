package com.zsl0.auth.core.interceptor;

import com.zsl0.auth.core.annotation.Permissions;
import com.zsl0.auth.core.annotation.RequireAuthentication;
import com.zsl0.auth.core.function.ExtendFunction;
import com.zsl0.auth.core.model.DefaultUserDetails;
import com.zsl0.auth.core.model.PermissionProvide;
import com.zsl0.auth.core.util.HttpUtil;
import com.zsl0.auth.core.util.SecurityContextHolder;
import com.zsl0.auth.core.util.TokenUtil;
import com.zsl0.component.common.core.exception.auth.authentication.NotAuthenticationException;
import com.zsl0.component.common.core.exception.auth.authentication.NotAuthorizationException;
import com.zsl0.component.common.core.exception.auth.token.TokenExpireException;
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

    private ExtendFunction extend;

    public AuthSecurityInterceptor(PermissionProvide permissionProvide, ExtendFunction extend) {
        this.permissionProvide = permissionProvide;
        this.extend = extend;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取userId
        String userId = this.checkToken(request);

        // 检查认证
        if (handler instanceof HandlerMethod) {
            this.checkAuthentication((HandlerMethod) handler, userId);
            // 检查权限
            this.checkPermissions((HandlerMethod) handler, userId);
            // 扩展操作，对需要登录或者权限的请求
            this.extend((HandlerMethod) handler);
        }
        return true;
    }

    /**
     * 检查认证
     */
    private void checkAuthentication(HandlerMethod handler, String userId) {
        boolean hasAuthentication = handler.hasMethodAnnotation(RequireAuthentication.class);
        if (hasAuthentication && userId == null) {
            throw new NotAuthenticationException("not login");
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

        if (Objects.isNull(userId)) {
            throw new NotAuthenticationException("require login");
        }

        Permissions methodAnnotation = handler.getMethodAnnotation(Permissions.class);
        if (Objects.isNull(methodAnnotation)) {
            // todo 记录获取方法注解失败 按理上面判断存在注解，此时不为空
            return;
        }
        String permission = methodAnnotation.value();

        // 检查权限 需要自定义实现permissionService
        if (!permissionProvide.hasPermission(permission)) {
            throw new NotAuthorizationException();
        }
    }

    /**
     * 扩展操作
     */
    private void extend(HandlerMethod handler) {
        boolean requirePermissions = handler.hasMethodAnnotation(Permissions.class);
        boolean hasAuthentication = handler.hasMethodAnnotation(RequireAuthentication.class);

        if (requirePermissions || hasAuthentication) {
            extend.run();
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
        if (TokenUtil.isExpire(token)) {
            throw new TokenExpireException("token has expired");
        }

        // 根据token获取uuid、permissions
        String details = TokenUtil.getDetails(token);
        String uuid = TokenUtil.getUuid(token);
        Long userId = TokenUtil.getUserId(token);
        Boolean authenticated = TokenUtil.getAuthenticated(token);
        Boolean admin = TokenUtil.getAdmin(token);
        List<String> permissions = TokenUtil.getPermissions(token);

        // 将凭证放入全局结构体中
        SecurityContextHolder.setAuth(DefaultUserDetails.builder()
                .details(details) // 暂不使用
                .uuid(uuid)
                .userId(userId)
                .authenticated(authenticated)
                .admin(admin)
                .permissions(permissions.toArray(new String[]{}))
                .build());
        return uuid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.clear();
    }
}
