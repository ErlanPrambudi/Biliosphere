package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:40 PM
@Last Modified 1/30/2025 6:40 PM
Version 1.0
*/


import com.fasterxml.jackson.annotation.JsonProperty;

public class RespGroupMenuDTO {
    private Long id;

    @JsonProperty("nama")
    private String namaGroupMenu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaGroupMenu() {
        return namaGroupMenu;
    }

    public void setNamaGroupMenu(String namaGroupMenu) {
        this.namaGroupMenu = namaGroupMenu;
    }
}

