package com.example.biliosphere2.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 12:43 PM
@Last Modified 1/30/2025 12:43 PM
Version 1.0
*/

public class ResponseHandler {

    public ResponseEntity<Object> handleResponse(String message,
                                                 HttpStatus status,
                                                 Object obj,
                                                 Object errorCode,
                                                 HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        map.put("status",status.value());
        map.put("data",obj==null?"":obj);
        map.put("timestamp",new Date());
        map.put("success",!status.isError());
        if(errorCode!=null) {
            map.put("error-code",errorCode);
            map.put("path",request.getContextPath());
        }
        return new ResponseEntity<>(map,status);
    }
}
