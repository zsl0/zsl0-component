package com.zsl0.component.auth.core.util;

import com.zsl0.component.auth.core.model.AuthInfo;

/**
 * 存储全局用户认证信息
 *
 * @author zsl0
 * create on 2022/5/15 21:12
 * email 249269610@qq.com
 */
public class SecurityContextHolder {
    private static ThreadLocal<AuthInfo> SECURITY_CONTEXT = new ThreadLocal<>();
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

    public static void setAuth(AuthInfo authentication) {
        SECURITY_CONTEXT.set(authentication);
    }

    public static AuthInfo getAuth() {
        return SECURITY_CONTEXT.get();
    }

    public static void clear() {
        SECURITY_CONTEXT.remove();
    }
}
