package com.example.biliosphere.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/26/2025 1:59 PM
@Last Modified 1/26/2025 1:59 PM
Version 1.0
*/

import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
