package com.zsl0.auth.config;

import com.zsl0.auth.core.interceptor.AuthSecurityInterceptor;
import com.zsl0.auth.core.util.HttpUtil;
import com.zsl0.auth.core.util.TokenUtil;
import com.zsl0.auth.core.function.ExtendFunction;
import com.zsl0.auth.core.function.DefaultExtendFunction;
import com.zsl0.auth.core.model.DefaultPermissionProvider;
import com.zsl0.auth.core.model.PermissionProvide;
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

    private final SecurityAdminConfigurationProperties properties;

    private static PermissionProvide permissionProvide;

    private static ExtendFunction extendFunction;

    public SecurityAdminAutoConfiguration(SecurityAdminConfigurationProperties properties) {
        this.properties = properties;
        // todo 解决动态修改静态变量问题
        TokenUtil.CERTIFICATE_KEY = properties.getCertificateKey();
        TokenUtil.PERMISSIONS_KEY = properties.getPermissionsKey();
        TokenUtil.ISSUER = properties.getIssuer();
        TokenUtil.SECRET = properties.getSecret();
        HttpUtil.HEAD = properties.getRequestHead();
    }

    // ==========================   拦截器   ==========================
    @Bean
    public AuthSecurityInterceptor authSecurityInterceptor(PermissionProvide permissionProvide, ExtendFunction extendFunction) {
        SecurityAdminAutoConfiguration.permissionProvide = permissionProvide;
        SecurityAdminAutoConfiguration.extendFunction = extendFunction;
        return new AuthSecurityInterceptor(permissionProvide, extendFunction);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthSecurityInterceptor authSecurityInterceptor = this.authSecurityInterceptor(SecurityAdminAutoConfiguration.permissionProvide, SecurityAdminAutoConfiguration.extendFunction);
        registry.addInterceptor(authSecurityInterceptor).excludePathPatterns(properties.getIgnorePath()).order(200);
    }

    @Bean
    @ConditionalOnMissingBean(PermissionProvide.class)
    public PermissionProvide permissionProvider() {
        return new DefaultPermissionProvider();
    }

    @Bean
    @ConditionalOnMissingBean(ExtendFunction.class)
    public DefaultExtendFunction extendFunction() {
        return new DefaultExtendFunction();
    }

}
