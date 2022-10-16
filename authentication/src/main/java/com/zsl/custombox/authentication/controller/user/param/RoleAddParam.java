package com.zsl.custombox.authentication.controller.user.param;

import lombok.Data;

/**
 * @Author zsl
 * @Date 2022/6/15 11:03
 * @Email 249269610@qq.com
 */
@Data
public class RoleAddParam {
    private String roleName;
    private String[] menus;
}
