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
Created on 1/9/2025 11:52 PM
@Last Modified 1/9/2025 11:52 PM
Version 1.0
*/
@Entity
@Table(name = "MstGender")
public class Genders {
    @Id
    @Column(name = "GenderId")
    private Long id;

    @Column(name = "GenderName",unique = true,nullable = false,length = 20)
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
