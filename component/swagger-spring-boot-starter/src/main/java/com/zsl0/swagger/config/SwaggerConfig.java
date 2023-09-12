package com.zsl0.swagger.config;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * swagger配置
 * <p>
 * swagger:
 * enable: true
 * controller-base-package: com.zsl0.xxx.xxx.controller (controller包路径)
 * application-name: xxx项目API文档
 * application-version: V0.0.1
 * application-description: 功能描述
 *
 * @author zsl0
 * create on 2022/5/15 17:45
 * email 249269610@qq.com
 */
@EnableConfigurationProperties(SwaggerConfigurationProperties.class)
@EnableSwagger2WebMvc
public class SwaggerConfig {
    private final SwaggerConfigurationProperties swaggerProperties;

    public SwaggerConfig(SwaggerConfigurationProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket productApi() {
        checkParams(swaggerProperties);

        //schema
        List<GrantType> grantTypes = new ArrayList<>();
        //密码模式
        String passwordTokenUrl = swaggerProperties.getPasswordTokenUrl();
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
        grantTypes.add(resourceOwnerPasswordCredentialsGrant);
        OAuth oAuth = new OAuthBuilder().name("oauth2")
                .grantTypes(grantTypes).build();
        //context
        //scope方位
        List<AuthorizationScope> scopes = new ArrayList<>();
        scopes.add(new AuthorizationScope("read", "read  resources"));
        scopes.add(new AuthorizationScope("write", "write resources"));
        scopes.add(new AuthorizationScope("reads", "read all resources"));
        scopes.add(new AuthorizationScope("writes", "write all resources"));

        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
        SecurityContext securityContext = new SecurityContext(Lists.newArrayList(securityReference), PathSelectors.ant("/api/**"));
        //schemas
        List<SecurityScheme> securitySchemes = Lists.newArrayList(oAuth);
        //securyContext
        List<SecurityContext> securityContexts = Lists.newArrayList(securityContext);
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getControllerBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts)
                .securitySchemes(securitySchemes)
                .apiInfo(apiInfo())
                .enable(swaggerProperties.getEnable());
//                .groupName(swaggerProperties.getGroupName());
    }


    private void checkParams(SwaggerConfigurationProperties swaggerProperties) {
        if (Objects.isNull(swaggerProperties.getControllerBasePackage()))
            throw new IllegalArgumentException("swagger.controller-base-package don't null! Please use yaml configuration swagger.controllerBasePackage");

        if (Objects.isNull(swaggerProperties.getEnable())) swaggerProperties.setEnable(false);

        if (Objects.isNull(swaggerProperties.getApplicationVersion()))
            swaggerProperties.setApplicationVersion("ApplicationVersion is empty.(config file -> swagger.application-version: 'your ApplicationVersion')");

        if (Objects.isNull(swaggerProperties.getApplicationDescription()))
            swaggerProperties.setApplicationDescription("description is empty.(config file -> swagger.application-description: 'your ApplicationDescription')");

        if (Objects.isNull(swaggerProperties.getApplicationName()))
            swaggerProperties.setApplicationName("ApplicationName is empty.(config file -> swagger.application-name: 'your ApplicationName')");

        if (Objects.isNull(swaggerProperties.getPasswordTokenUrl()))
            swaggerProperties.setPasswordTokenUrl("PasswordTokenUrl is empty.(config file -> swagger.password-token-url: 'your PasswordTokenUrl')");

        if (Objects.isNull(swaggerProperties.getGroupName()))
            swaggerProperties.setGroupName("GroupName is empty.(config file -> swagger.group-name: 'your GroupName')");

        if (Objects.isNull(swaggerProperties.getServiceUrl()))
            swaggerProperties.setServiceUrl("ServiceUrl is empty.(config file -> swagger.service-url: 'your ServiceUrl')");

        if (Objects.isNull(swaggerProperties.getBasePath()))
            swaggerProperties.setBasePath("BasePath is empty.(config file -> swagger.base-path: 'your BasePath')");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .termsOfServiceUrl(swaggerProperties.getServiceUrl())
                .contact(new Contact("zsl0", "http://home.zsl0.com:8889", "249269610@qq.com"))
                .license("Open Source")
                .licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0")
                .title(swaggerProperties.getApplicationName())
                .description(swaggerProperties.getApplicationDescription())
                .version(swaggerProperties.getApplicationVersion())
                .build();

    }

//    @Bean
//    public Docket petApi() {
//        // 检查参数
//        checkParams(swaggerProperties);
//
//        return new Docket(DocumentationType.SWAGGER_2)
//
//                // api文档信息
//                .apiInfo(apiInfo())
//
//                // 分组名称
//                .groupName(swaggerProperties.getApplicationVersion())
//
//                // 定义是否开启swagger,false为关闭，可以通过yaml配置变量控制
//                .enable(swaggerProperties.getEnable())
//
//                // 选择那些接口作为swagger的doc发布
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getControllerBasePackage()))    // api路径
//                .paths(PathSelectors.any()) // 路径匹配
//                .build()
//
//                // 授权信息全局应用
//                .securityContexts(securityContexts())
//
//                // 授权信息设置，必要的header token等认证信息
//                .securitySchemes(apiKeys());
//    }
//
//    private void checkParams(SwaggerConfigurationProperties swaggerProperties) {
//        if (Objects.isNull(swaggerProperties.getTryHost())) swaggerProperties.setTryHost("-");
//
//        if (Objects.isNull(swaggerProperties.getControllerBasePackage()))
//            throw new IllegalArgumentException("swagger controller basePackage don't null! Please use yaml configuration swagger.controllerBasePackage");
//
//        if (Objects.isNull(swaggerProperties.getEnable())) swaggerProperties.setEnable(false);
//
//        if (Objects.isNull(swaggerProperties.getApplicationVersion())) swaggerProperties.setApplicationVersion("default-version");
//
//        if (Objects.isNull(swaggerProperties.getApplicationDescription())) swaggerProperties.setApplicationDescription("-");
//
//        if (Objects.isNull(swaggerProperties.getApplicationName())) swaggerProperties.setApplicationName("swagger API文档");
//    }
//
//    /**
//     * API 页面上半部分展示信息
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(swaggerProperties.getApplicationName())
//                .description(swaggerProperties.getApplicationDescription())
//                .version("Application Version: " + swaggerProperties.getApplicationVersion())
//                .build();
//    }
//
//    /**
//     * 设置授权信息
//     */
//    private List<SecurityScheme> apiKeys() {
//        List<SecurityScheme> list = new ArrayList<>();
//        list.add(new ApiKey("Bearer Token", "Authorization", "header"));
//        return list;
//    }
//
//    /**
//     * 授权信息全局应用
//     */
//    private List<SecurityContext> securityContexts() {
//        List<SecurityContext> list = new ArrayList<>();
//        list.add(SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/.*"))
//                .build());
//        return list;
//    }
//
//    /**
//     *
//     */
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("Bearer Token", authorizationScopes));
//    }
}