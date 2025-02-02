package com.example.biliosphere2.config;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/2/2025 4:37 PM
@Last Modified 2/2/2025 4:37 PM
Version 1.0
*/

import com.example.biliosphere2.security.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class MainConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource getDatasource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSourceBuilder.url(Crypto.performDecrypt(env.getProperty("spring.datasource.url")));
        dataSourceBuilder.username(Crypto.performDecrypt(env.getProperty("spring.datasource.username")));
        dataSourceBuilder.password(Crypto.performDecrypt(env.getProperty("spring.datasource.password")));
        return dataSourceBuilder.build();
    }
}
