package com.zsl.custombox.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author zsl
 * @Date 2022/5/15 15:57
 * @Email 249269610@qq.com
 */
@SpringBootApplication(scanBasePackages = {"com.zsl.custombox.common", "com.zsl.custombox.authentication"})
public class AuthenticationApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApp.class);
    }
}
