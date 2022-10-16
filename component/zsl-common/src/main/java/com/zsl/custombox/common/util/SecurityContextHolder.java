package com.zsl.custombox.common.util;

import com.zsl.custombox.common.model.authentication.Authentication;

import java.util.Optional;

/**
 * 存储全局用户认证信息 todo 定义凭证对象接口
 *
 * @Author zsl
 * @Date 2022/5/15 21:12
 * @Email 249269610@qq.com
 */
public class SecurityContextHolder {
    private static ThreadLocal<Authentication> SECURITY_CONTEXT = new ThreadLocal<>();

    public static Authentication getAuth() {
        Authentication authentication = Optional.ofNullable(SECURITY_CONTEXT.get()).orElse(new Authentication() {
            @Override
            public String getUuid() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Long getUserId() {
                return -1L;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String[] getRoles() {
                return new String[0];
            }

            @Override
            public boolean isAdmin() {
                return false;
            }
        });
        setAuth(authentication);
        return authentication;
    }

    public static void setAuth(Authentication authentication) {
        SECURITY_CONTEXT.set(authentication);
    }

    public static void clear() {
        SECURITY_CONTEXT.remove();
    }
}
