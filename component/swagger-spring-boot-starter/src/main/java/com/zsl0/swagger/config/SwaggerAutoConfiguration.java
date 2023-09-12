package com.zsl0.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * swagger自动配置类
 *
 * @author zsl0
 * create on 2022/5/31 22:20
 * email 249269610@qq.com
 */
@Configuration
@Import(SwaggerConfig.class)
public class SwaggerAutoConfiguration {
}
