package com.zsl0.component.auth.config;

import com.zsl0.component.auth.core.interceptor.AuthSecurityInterceptor;
import com.zsl0.component.auth.core.model.DefaultPermissionProvider;
import com.zsl0.component.auth.core.model.PermissionProvide;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志自动配置
 *
 * @author zsl0
 * create on 2022/5/22 21:26
 * email 249269610@qq.com
 */
@Configuration
@Import(SecurityAdminConfigurationProperties.class)
public class SecurityAdminAutoConfiguration implements WebMvcConfigurer {

    private SecurityAdminConfigurationProperties properties;

    private static PermissionProvide permissionProvide;

    public SecurityAdminAutoConfiguration(SecurityAdminConfigurationProperties properties) {
        this.properties = properties;
    }

    // ==========================   拦截器   ==========================
    @Bean
    public AuthSecurityInterceptor authSecurityInterceptor(PermissionProvide permissionProvide) {
        SecurityAdminAutoConfiguration.permissionProvide = permissionProvide;
        return new AuthSecurityInterceptor(permissionProvide);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthSecurityInterceptor authSecurityInterceptor = this.authSecurityInterceptor(SecurityAdminAutoConfiguration.permissionProvide);
        registry.addInterceptor(authSecurityInterceptor).excludePathPatterns(properties.getIgnorePath());
    }

    @Bean
    @ConditionalOnMissingBean(PermissionProvide.class)
    public PermissionProvide permissionProvider() {
        return new DefaultPermissionProvider();
    }

}
