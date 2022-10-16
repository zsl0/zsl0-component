package com.zsl.custombox.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * swagger自动配置类
 *
 * @Author zsl
 * @Date 2022/5/31 22:20
 * @Email 249269610@qq.com
 */
@Configuration
@Import(SwaggerConfig.class)
public class SwaggerAutoConfiguration {
}
