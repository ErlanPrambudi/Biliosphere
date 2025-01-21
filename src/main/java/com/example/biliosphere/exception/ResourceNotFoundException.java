package com.example.biliosphere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 3:37 PM
@Last Modified 1/20/2025 3:37 PM
Version 1.0
*/

@ResponseStatus(code = HttpStatus.NOT_FOUND) // Memberikan status 404 saat exception dilempar
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
