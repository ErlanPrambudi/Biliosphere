package com.example.biliosphere.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 5:09 PM
@Last Modified 1/8/2025 5:09 PM
Version 1.0
*/

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MstCategory")
public class Categories {

    @Id
    @Column(name = "CategoryId")
    private Long id;

    @Column(name = "CategoryName",unique = true,nullable = false,length = 20)
    private String namaCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaCategory() {
        return namaCategory;
    }

    public void setNamaCategory(String namaCategory) {
        this.namaCategory = namaCategory;
    }
}
