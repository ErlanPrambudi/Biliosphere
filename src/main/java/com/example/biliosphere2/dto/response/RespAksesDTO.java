package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/29/2025 10:54 PM
@Last Modified 1/29/2025 10:54 PM
Version 1.0
*/

import com.example.biliosphere2.model.Menu;

import java.util.List;


public class RespAksesDTO {
    private Long id;
    private String nama;

    private List<Menu> ltMenu;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Menu> getLtMenu() {
        return ltMenu;
    }

    public void setLtMenu(List<Menu> ltMenu) {
        this.ltMenu = ltMenu;
    }
}

