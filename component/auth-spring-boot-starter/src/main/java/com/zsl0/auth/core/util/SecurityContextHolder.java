package com.zsl0.auth.core.util;

import com.zsl0.auth.core.model.Authentication;

import java.util.Objects;

/**
 * 存储全局用户认证信息
 *
 * @author zsl0
 * create on 2022/5/15 21:12
 * email 249269610@qq.com
 */
public class SecurityContextHolder {

    // todo 解决ThreadLocal线程安全问题
    private static final ThreadLocal<Authentication> SECURITY_CONTEXT = new ThreadLocal<>();

    public static void setAuth(Authentication authentication) {
        SECURITY_CONTEXT.set(authentication);
    }

    public static Authentication getAuth() {
        return SECURITY_CONTEXT.get();
    }

    public static void clear() {
        SECURITY_CONTEXT.remove();
    }

    public static boolean isLogin() {
        return Objects.nonNull(SECURITY_CONTEXT.get());
    }
}
