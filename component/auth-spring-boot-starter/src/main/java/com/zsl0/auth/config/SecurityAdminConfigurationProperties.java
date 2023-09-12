package com.zsl0.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * @author zsl0
 * create on 2022/6/6 14:58
 * email 249269610@qq.com
 */
@ConfigurationProperties(prefix = "security.admin")
public class SecurityAdminConfigurationProperties {

    /**
     * 忽略路径
     */
    private String[] ignorePath;

    /**
     * 用户凭证key todo 静态参数提供动态获取
     */
    private String certificateKey = "UUID";

    /**
     * 许可key
     */
    private String permissionsKey = "PERMISSIONS";

    /**
     * 发行人
     */
    private String issuer = "zsl0";

    /**
     * 密钥
     */
    private String secret = "zsl0:abc123456";

    /**
     * 请求头
     */
    public String requestHead = "Authorization-zsl0";

    private static final String[] DEFAULT_IGNORE_PATH = {
            // swagger相关
            "/doc.html", "/swagger-resources", "/swagger-resources/**", "/webjars/**",
            // 文件相关
            "/**/*.*",
    };

    public String[] getIgnorePath() {
        return Objects.isNull(ignorePath) ? DEFAULT_IGNORE_PATH : ignorePath;
    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public void setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
    }

    public String getPermissionsKey() {
        return permissionsKey;
    }

    public void setPermissionsKey(String permissionsKey) {
        this.permissionsKey = permissionsKey;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRequestHead() {
        return requestHead;
    }

    public void setRequestHead(String requestHead) {
        this.requestHead = requestHead;
    }

    public void setIgnorePath(String[] ignorePath) {
        this.ignorePath = ignorePath;
    }

}
