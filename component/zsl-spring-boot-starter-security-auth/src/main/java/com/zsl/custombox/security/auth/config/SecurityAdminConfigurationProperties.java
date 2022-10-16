package com.zsl.custombox.security.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * @Author zsl
 * @Date 2022/6/6 14:58
 * @Email 249269610@qq.com
 */
@ConfigurationProperties(prefix = "security.admin")
public class SecurityAdminConfigurationProperties {
    // 忽略路径
    private String[] ignorePath;

    private static final String[] DEFAULT_IGNORE_PATH = {
            // swagger相关
            "/doc.html", "/swagger-resources", "/swagger-resources/**", "/webjars/**",
            // 文件相关
            "/**/*.*",
    };

    public String[] getIgnorePath() {
        return Objects.isNull(ignorePath) ? DEFAULT_IGNORE_PATH : ignorePath;
    }
}
