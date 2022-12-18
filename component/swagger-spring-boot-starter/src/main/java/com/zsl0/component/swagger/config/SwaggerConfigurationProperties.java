package com.zsl0.component.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger配置资源
 *
 * @author zsl0
 * create on 2022/5/31 22:21
 * email 249269610@qq.com
 */
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigurationProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enable = true;

    /**
     * 应用程序名称
     */
    private String applicationName;

    /**
     * 应用程序版本
     */
    private String applicationVersion;

    /**
     * 应用程序描述
     */
    private String applicationDescription;

    /**
     * 接口调试地址
     */
    private String tryHost;

    /**
     * controller扫描包
     */
    private String controllerBasePackage;

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

    public String getControllerBasePackage() {
        return controllerBasePackage;
    }

    public void setControllerBasePackage(String controllerBasePackage) {
        this.controllerBasePackage = controllerBasePackage;
    }
}
