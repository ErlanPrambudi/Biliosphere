package com.example.biliosphere2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwtproperties.properties")
public class JwtConfig {


    private static String jwtSecret;
    private static String jwtExpiration;

    public static String getJwtSecret() {
        return jwtSecret;
    }

    @Value("${jwt.secret}")
    private void setJwtSecret(String jwtSecret) {
        JwtConfig.jwtSecret = jwtSecret;
    }

    public static String getJwtExpiration() {
        return jwtExpiration;
    }

    @Value("${jwt.expiration}")
    private void setJwtExpiration(String jwtExpiration) {
        JwtConfig.jwtExpiration = jwtExpiration;
    }
}
