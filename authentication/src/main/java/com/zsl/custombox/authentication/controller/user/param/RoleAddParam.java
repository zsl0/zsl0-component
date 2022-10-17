package com.zsl.custombox.authentication.controller.user.param;

import lombok.Data;

/**
 * @author zsl0
 * create on 2022/6/15 11:03
 * email 249269610@qq.com
 */
@Data
public class RoleAddParam {
    private String roleName;
    private String[] menus;
}
