package com.zsl.custombox.authentication.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 开启mapper扫描
 *
 * @Author zsl
 * @Date 2022/6/8 16:52
 * @Email 249269610@qq.com
 */
@Configuration
@MapperScan(basePackages = "com.zsl.custombox.authentication.mapper")
public class MybatisConfig {
}
