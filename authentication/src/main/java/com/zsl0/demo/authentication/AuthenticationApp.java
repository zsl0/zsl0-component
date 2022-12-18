package com.zsl0.demo.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zsl0
 * create on 2022/5/15 15:57
 * email 249269610@qq.com
 */
@SpringBootApplication(scanBasePackages = {"com.zsl.custombox.common", "com.zsl.custombox.authentication"})
public class AuthenticationApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApp.class);
    }
}
