package com.zsl0.auth;

import com.zsl0.auth.config.DemoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author zsl0
 * created on 2022/12/12 22:38
 */
@SpringBootApplication
//@EnableConfigurationProperties({
//        DemoConfiguration.class
//})
public class AuthDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthDemoApp.class);
    }
}
