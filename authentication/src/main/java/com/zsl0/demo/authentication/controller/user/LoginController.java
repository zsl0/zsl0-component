package com.zsl0.demo.authentication.controller.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author zsl0
 * create on 2022/6/5 11:12
 * email 249269610@qq.com
 */
@RestController
@RequestMapping("/user/login")
@Api(tags = "登录模块")
public class LoginController {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    TokenServer tokenServer;
//
//    @Autowired
//    MenuService menuService;
//
//    @PostMapping
//    @ApiOperation("用户名密码登录")
//    public UserInfoVo usernamePasswordLogin(@ApiParam("账号密码登录") @RequestBody @Valid UsernamePasswordLoginParam usernamePasswordLoginParam) {
//        DefaultUserDetails defaultUserDetails = userDetailsService.loadUserByUsername(usernamePasswordLoginParam.getUsername());
//
//        // 生成Token
//        String uuid = UUID.randomUUID().toString();
//        String accessToken = TokenUtil.createAccessToken(uuid);
//        defaultUserDetails.setUuid(uuid);
//        SecurityContextHolder.setAuth(defaultUserDetails);
//
//        // 缓存用户信息
//        tokenServer.set(uuid, JsonUtil.obj2Str(defaultUserDetails));
//
//        List<MenuNode> menuNodes;
//        // 查询菜单
//        if (defaultUserDetails.isAdmin()) {
//            menuNodes = menuService.loadAll();
//        } else {
//            menuNodes = menuService.loadUserMenu(defaultUserDetails.getUserId());
//        }
//
//        // 返回token
//        return UserInfoVo.builder().accessToken(accessToken).menus(menuNodes.toArray(new MenuNode[0])).build();
//    }
}
