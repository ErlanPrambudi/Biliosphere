package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/31/2025 12:50 AM
@Last Modified 1/31/2025 12:50 AM
Version 1.0
*/


public class RespMenuDTO {

    private Long id;
    private String nama;
    private String path;
    private RespGroupMenuDTO groupMenu;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RespGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(RespGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}
