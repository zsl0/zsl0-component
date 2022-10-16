package com.zsl.custombox.authentication.model.param.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Author zsl
 * @Date 2022/6/5 11:21
 * @Email 249269610@qq.com
 */
@ApiModel("用户名密码登录")
@Valid
@Data
public class UsernamePasswordLoginParam {
    @ApiModelProperty(value = "用户名称", example = "admin")
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @ApiModelProperty(value = "用户密码", example = "cqkj123456")
    @NotBlank(message = "密码不能为空！")
    private String password;
    @ApiModelProperty(value = "验证码", example = "abc")
//    @NotBlank(message = "验证码不能为空！")
    private String code;
}
