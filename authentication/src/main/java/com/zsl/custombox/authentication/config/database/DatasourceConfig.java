package com.zsl.custombox.authentication.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zsl.custombox.common.util.CryptoUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author zsl
 * @Date 2022/4/19 16:21
 * @Email 249269610@qq.com
 */
@Configuration
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties("datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        // 解密
        hikariConfig.setPassword(CryptoUtil.decodeBase64(hikariConfig.getPassword()));
        return new HikariDataSource(hikariConfig);
    }
}
