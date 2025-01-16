package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "GenderName", unique = true, nullable = false, length = 20)
    private String gender;

    @OneToMany(mappedBy = "gender")
    private List<Users> users;

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

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
