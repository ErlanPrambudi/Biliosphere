package com.example.biliosphere2.dto.response;
/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 8:12 PM
@Last Modified 1/28/2025 8:12 PM
Version 1.0
*/

public class MenuLoginDTO {
    private String nama;
    private String path;
    private GroupMenuLoginDTO groupMenu;

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

    public GroupMenuLoginDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(GroupMenuLoginDTO groupMenu) {
        this.groupMenu = groupMenu;
    }
}
