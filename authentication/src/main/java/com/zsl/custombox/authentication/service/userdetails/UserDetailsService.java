package com.zsl.custombox.authentication.service.userdetails;

import com.zsl.custombox.security.auth.core.model.DefaultUserDetails;

/**
 * 认证用户查询接口
 *
 * @Author zsl
 * @Date 2022/6/8 16:35
 * @Email 249269610@qq.com
 */
public interface UserDetailsService {

    /**
     * 根据用户名,查询用户信息
     */
    DefaultUserDetails loadUserByUsername(String username);

    /**
     * 根据邮箱,查询用户信息
     */
    DefaultUserDetails loadUserByEmail(String email);

    /**
     * 根据手机号,查询用户信息
     */
    DefaultUserDetails loadUserByPhone(String phone);
}
