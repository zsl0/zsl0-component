package com.zsl0.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zsl0
 * created on 2022/12/17 10:14
 */
@Configuration
@ConfigurationProperties(prefix = "demo")
public class DemoConfiguration {

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
