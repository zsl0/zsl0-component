package com.zsl0.auth.controller;

import com.zsl0.component.auth.core.annotation.Permissions;
import com.zsl0.component.auth.core.util.TokenUtil;
import com.zsl0.component.log.core.annotation.LogRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zsl0
 * created on 2022/12/12 22:40
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("pwd")
    public String login(@RequestBody PwdLogin loginInfo) {
        List<String> permissions = new ArrayList<>();
        permissions.add("admin");
        permissions.add("system");
        String access_token = TokenUtil.generateToken("access_token", new Date(2022, Calendar.DECEMBER, 13, 12, 0), "1", permissions);
        return "Bearer " + access_token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class PwdLogin {
        private String username;
        private String password;
    }

    @PostMapping("email")
    public String login1() {
        return "susscee";
    }

    @GetMapping("phone")
    public String admin(String username) {
        return "success";
    }

    @GetMapping("system")
    @Permissions("system")
    @LogRecord(value = "system进行查询", bizNo = "web")
    public String system() {
        return "success";
    }

    @GetMapping("audit")
    @Permissions("audit")
    public String audit() {
        return "success";
    }

}
