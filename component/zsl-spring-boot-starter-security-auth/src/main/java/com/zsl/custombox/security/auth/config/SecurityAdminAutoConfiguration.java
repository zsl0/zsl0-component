package com.zsl.custombox.security.auth.config;

import com.zsl.custombox.common.core.service.cache.TokenServer;
import com.zsl.custombox.log.config.LogAutoConfiguration;
import com.zsl.custombox.security.auth.core.interceptor.SecurityContextInterceptor;
import com.zsl.custombox.security.auth.core.interceptor.AuthSecurityInterceptor;
import com.zsl.custombox.security.auth.core.model.DefaultPermissionServiceImpl;
import com.zsl.custombox.security.auth.core.model.DefaultTokenServiceImpl;
import com.zsl.custombox.security.auth.core.model.PermissionService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 日志自动配置
 *
 * @Author zsl
 * @Date 2022/5/22 21:26
 * @Email 249269610@qq.com
 */
@Configuration
@AutoConfigureAfter(LogAutoConfiguration.class)
@Import(SecurityAdminConfigurationProperties.class)
public class SecurityAdminAutoConfiguration implements WebMvcConfigurer {

    private SecurityAdminConfigurationProperties properties;

    public SecurityAdminAutoConfiguration(SecurityAdminConfigurationProperties properties) {
        this.properties = properties;
    }

    // ==========================   拦截器   ==========================
    @Bean
    public SecurityContextInterceptor securityContextInterceptor() {
        return new SecurityContextInterceptor();
    }

    @Bean
    public AuthSecurityInterceptor authSecurityInterceptor() {
        AuthSecurityInterceptor authSecurityInterceptor = new AuthSecurityInterceptor();
        authSecurityInterceptor.setTokenServer(this.tokenServer());
        authSecurityInterceptor.setPermissionService(this.permissionService());
        return authSecurityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authSecurityInterceptor()).excludePathPatterns(properties.getIgnorePath());
        registry.addInterceptor(this.securityContextInterceptor()).excludePathPatterns(properties.getIgnorePath());
    }

    @Bean
    @ConditionalOnMissingBean(TokenServer.class)
    public TokenServer tokenServer() {
        return new DefaultTokenServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(PermissionService.class)
    public PermissionService permissionService() {
        return new DefaultPermissionServiceImpl();
    }

}
