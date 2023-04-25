package com.zsl0.component.auth.core.util;

import com.zsl0.component.auth.core.model.Authentication;

import java.util.Objects;

/**
 * 存储全局用户认证信息
 *
 * @author zsl0
 * create on 2022/5/15 21:12
 * email 249269610@qq.com
 */
public class SecurityContextHolder {
    private static final ThreadLocal<Authentication> SECURITY_CONTEXT = new ThreadLocal<>();
//
//    public static Authentication getAuth() {
//        Authentication authentication = Optional.ofNullable(SECURITY_CONTEXT.get()).orElse(new Authentication() {
//            @Override
//            public String getUuid() {
//                return null;
//            }
//
//            @Override
//            public Object getDetails() {
//                return null;
//            }
//
//            @Override
//            public Long getUserId() {
//                return -1L;
//            }
//
//            @Override
//            public boolean isAuthenticated() {
//                return false;
//            }
//
//            @Override
//            public String[] getRoles() {
//                return new String[0];
//            }
//
//            @Override
//            public boolean isAdmin() {
//                return false;
//            }
//        });
//        setAuth(authentication);
//        return authentication;
//    }

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
