package com.example.biliosphere2.util;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 12:54 PM
@Last Modified 1/30/2025 12:54 PM
Version 1.0
*/


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingFile {
    private static StringBuilder sBuild = new StringBuilder();

    private static Logger logger = LogManager.getLogger(LoggingFile.class);

    public static void logException(String strClass, String strMethod,
                                    Exception e, String strFlag) {
        if(strFlag.equals("y")){
            sBuild.setLength(0);
            logger.error(sBuild.append(System.getProperty("line.separator")).
                    append("ERROR IN CLASS ==> ").append(strClass).append(System.getProperty("line.separator")).
                    append("METHOD ==> ").append(strMethod).append(System.getProperty("line.separator")).
                    append("ERROR IS ==> ").append(e.getMessage()).append(System.getProperty("line.separator")));
        }
    }
}

