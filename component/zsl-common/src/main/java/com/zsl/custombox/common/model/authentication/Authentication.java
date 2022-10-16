package com.zsl.custombox.common.model.authentication;

/**
 * 认证接口
 *
 * @Author zsl
 * @Date 2022/5/16 22:32
 * @Email 249269610@qq.com
 */
public interface Authentication {

    // 认证信息暂定方法
    String getUuid();

    Object getDetails();

    Long getUserId();

    boolean isAuthenticated();

    String[] getRoles();

    boolean isAdmin();
}
