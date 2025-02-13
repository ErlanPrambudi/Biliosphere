//package com.example.biliosphere2.config;
//
///*
//IntelliJ IDEA 2024.3 (Ultimate Edition)
//Build #IU-243.21565.193, built on November 13, 2024
//@Author Dell Erlan Prambudi
//Java Developer
//Created on 2/8/2025 10:20 PM
//@Last Modified 2/8/2025 10:20 PM
//Version 1.0
//*/
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    @Bean
//    public StringRedisTemplate redisTemplate() {
//        return new StringRedisTemplate(redisConnectionFactory());
//    }
//}
