package com.zsl.custombox.authentication.controller.user.vo;

import com.zsl.custombox.authentication.model.pojo.login.MenuNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author zsl
 * @Date 2022/6/9 16:27
 * @Email 249269610@qq.com
 */
@Data
@Builder
@ApiModel("用户信息")
public class UserInfoVo {
    @ApiModelProperty(name = "访问token")
    private String accessToken;
    @ApiModelProperty(name = "刷新token")
    private String refreshToken;
    @ApiModelProperty(name = "用户菜单")
    private MenuNode[] menus;
}
