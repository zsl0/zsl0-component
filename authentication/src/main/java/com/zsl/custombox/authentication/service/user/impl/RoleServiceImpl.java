package com.zsl.custombox.authentication.service.user.impl;

import com.zsl.custombox.authentication.controller.user.Role2RoleVoMapper;
import com.zsl.custombox.authentication.controller.user.param.RoleAddParam;
import com.zsl.custombox.authentication.controller.user.param.RoleModifyParam;
import com.zsl.custombox.authentication.controller.user.vo.RoleVo;
import com.zsl.custombox.authentication.mapper.MenuMapper;
import com.zsl.custombox.authentication.mapper.RoleMapper;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import com.zsl.custombox.authentication.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zsl
 * @Date 2022/6/10 17:36
 * @Email 249269610@qq.com
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<RoleVo> roles() {
        List<Role> roles = roleMapper.queryAll();
        return Role2RoleVoMapper.INSTANCE.convert(roles);
    }

    @Override
    public void role(Long roleId) {

    }

    @Override
    public void addRole(RoleAddParam roleAddParam) {

    }

    @Override
    public void removeRole(Long roleId) {

    }

    @Override
    public void modifyRole(RoleModifyParam roleModifyParam) {

    }
}
