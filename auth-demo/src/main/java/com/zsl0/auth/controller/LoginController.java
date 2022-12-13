package com.zsl0.auth.controller;

import com.zsl0.component.auth.config.SecurityAdminConfigurationProperties;
import com.zsl0.component.auth.core.annotation.Permissions;
import com.zsl0.component.auth.core.annotation.RequireAuthentication;
import com.zsl0.component.auth.core.util.JwtUtil;
import com.zsl0.component.auth.core.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zsl0
 * created on 2022/12/12 22:40
 */
@RestController
public class LoginController {

    @GetMapping("login")
    public String login() {
        List<String> permissions = new ArrayList<>();
        permissions.add("admin");
        permissions.add("system");
        String access_token = TokenUtil.generateToken("access_token", new Date(2022, Calendar.DECEMBER, 13, 12, 0), "1", permissions);
        return "token=" + access_token;
    }

    @GetMapping("login1")
    @RequireAuthentication
    public String login1() {
        return "susscee";
    }

    @GetMapping("admin")
    @Permissions("admin")
    public String admin() {
        return "success";
    }

    @GetMapping("system")
    @Permissions("system")
    public String system() {
        return "success";
    }

    @GetMapping("audit")
    @Permissions("audit")
    public String audit() {
        return "success";
    }

}
