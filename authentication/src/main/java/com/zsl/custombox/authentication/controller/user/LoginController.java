package com.zsl.custombox.authentication.controller.user;

import com.zsl.custombox.authentication.controller.user.vo.UserInfoVo;
import com.zsl.custombox.authentication.model.param.login.UsernamePasswordLoginParam;
import com.zsl.custombox.authentication.model.pojo.login.MenuNode;
import com.zsl.custombox.authentication.service.user.MenuService;
import com.zsl.custombox.authentication.service.userdetails.UserDetailsService;
import com.zsl.custombox.common.core.service.cache.TokenServer;
import com.zsl.custombox.common.util.JsonUtil;
import com.zsl.custombox.common.util.SecurityContextHolder;
import com.zsl.custombox.common.util.TokenUtil;
import com.zsl.custombox.security.auth.core.model.DefaultUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 登录
 *
 * @Author zsl
 * @Date 2022/6/5 11:12
 * @Email 249269610@qq.com
 */
@RestController
@RequestMapping("/user/login")
@Api(value = "登录模块", tags = "登录模块")
public class LoginController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenServer tokenServer;

    @Autowired
    MenuService menuService;

    @PostMapping
    @ApiOperation("用户名密码登录")
    public UserInfoVo usernamePasswordLogin(@ApiParam("账号密码登录") @RequestBody @Valid UsernamePasswordLoginParam usernamePasswordLoginParam) {
        DefaultUserDetails defaultUserDetails = userDetailsService.loadUserByUsername(usernamePasswordLoginParam.getUsername());

        // 生成Token
        String accessToken = TokenUtil.createAccessToken();
        String accessTokenUuid = TokenUtil.getAccessTokenUuid(accessToken);
        defaultUserDetails.setUuid(accessTokenUuid);
        SecurityContextHolder.setAuth(defaultUserDetails);

        // 缓存用户信息
        tokenServer.set(accessTokenUuid, JsonUtil.obj2Str(defaultUserDetails));

        List<MenuNode> menuNodes;
        // 查询菜单
        if (defaultUserDetails.isAdmin()) {
            menuNodes = menuService.loadAll();
        } else {
            menuNodes = menuService.loadUserMenu(defaultUserDetails.getUserId());
        }

        // 返回token
        return UserInfoVo.builder().accessToken(accessToken).menus(menuNodes.toArray(new MenuNode[0])).build();
    }
}
