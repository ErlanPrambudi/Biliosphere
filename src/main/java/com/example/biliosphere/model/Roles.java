package com.example.biliosphere.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/9/2025 11:49 PM
@Last Modified 1/9/2025 11:49 PM
Version 1.0
*/
@Entity
@Table(name = "MstRole")
public class Roles {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "RoleName",unique = true,nullable = false,length = 20)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
