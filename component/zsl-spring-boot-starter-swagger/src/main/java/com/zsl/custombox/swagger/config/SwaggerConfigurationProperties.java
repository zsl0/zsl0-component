package com.zsl.custombox.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger配置资源
 *
 * @Author zsl
 * @Date 2022/5/31 22:21
 * @Email 249269610@qq.com
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigurationProperties {
    private Boolean enable = true; // 是否开启swagger
    private String applicationName; // 应用程序名称
    private String applicationVersion;  // 应用程序版本
    private String applicationDescription;  // 应用程序描述
    private String tryHost; //  接口调试地址

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public String getTryHost() {
        return tryHost;
    }

    public void setTryHost(String tryHost) {
        this.tryHost = tryHost;
    }
}
