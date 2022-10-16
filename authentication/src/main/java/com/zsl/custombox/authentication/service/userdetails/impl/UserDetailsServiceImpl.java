package com.zsl.custombox.authentication.service.userdetails.impl;

import com.zsl.custombox.authentication.mapper.RoleMapper;
import com.zsl.custombox.authentication.mapper.UserInfoMapper;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import com.zsl.custombox.authentication.model.pojo.login.UserInfo;
import com.zsl.custombox.authentication.service.userdetails.UserDetailsService;
import com.zsl.custombox.common.core.exception.AuthenticationFailedException;
import com.zsl.custombox.security.auth.core.model.DefaultUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 认证用户查询实现类
 *
 * @Author zsl
 * @Date 2022/6/8 16:37
 * @Email 249269610@qq.com
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public DefaultUserDetails loadUserByUsername(String username) {
        // 查询用户
        UserInfo userInfo = userInfoMapper.queryByUsername(username);
        return create(userInfo);
    }

    @Override
    public DefaultUserDetails loadUserByEmail(String email) {
        // 查询用户
        UserInfo userInfo = userInfoMapper.queryByEmail(email);
        return create(userInfo);
    }

    @Override
    public DefaultUserDetails loadUserByPhone(String phone) {
        // 查询用户
        UserInfo userInfo = userInfoMapper.queryByPhone(phone);
        return create(userInfo);
    }

    /**
     * 根据查询用户，查询角色，并创建DefaultUserDetails
     */
    private DefaultUserDetails create(UserInfo userInfo){
        if (Objects.isNull(userInfo)) {
            throw new AuthenticationFailedException("username不存在！");
        }
        // 查询角色
        List<Role> roles = roleMapper.queryByUserId(userInfo.getId());
        return new DefaultUserDetails(userInfo, null, userInfo.getId(), true, userInfo.getAdmin() == 1L, roles.stream().map(Role::getRoleName).toArray(String[]::new));
    }
}
