package com.example.biliosphere2.config;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 7:28 PM
@Last Modified 1/28/2025 7:28 PM
Version 1.0
*/

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Random;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfig {

    private static String enableLogFile;
    private static String enableAutomation;

    public static String getEnableAutomation() {
        return enableAutomation;
    }

    @Value("${enable.automation}")
    private void setEnableAutomation(String enableAutomation) {
        OtherConfig.enableAutomation = enableAutomation;
    }

    public static String getEnableLogFile() {
        return enableLogFile;
    }

    @Value("${enable.logfile}")
    private void setEnableLogFile(String enableLogFile) {
        OtherConfig.enableLogFile = enableLogFile;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

