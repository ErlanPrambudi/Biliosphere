package com.example.biliosphere2.config;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/8/2025 12:41 AM
@Last Modified 2/8/2025 12:41 AM
Version 1.0
*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // ✅ Berlaku untuk semua endpoint
                        .allowedOrigins("http://localhost:5173") // ✅ Hanya izinkan frontend React
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // ✅ Tambahkan method lain
                        .allowedHeaders("*") // ✅ Izinkan semua header
                        .allowCredentials(true);
            }
        };
    }
}
