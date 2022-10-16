package com.zsl.custombox.authentication.controller.user;

import com.zsl.custombox.authentication.controller.user.vo.RoleVo;
import com.zsl.custombox.authentication.model.pojo.login.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author zsl
 * @Date 2022/6/15 11:22
 * @Email 249269610@qq.com
 */
@Mapper
public interface Role2RoleVoMapper {

    Role2RoleVoMapper INSTANCE = Mappers.getMapper(Role2RoleVoMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "roleName", target = "roleName")
    RoleVo convert(Role role);

    List<RoleVo> convert(List<Role> roles);
}
