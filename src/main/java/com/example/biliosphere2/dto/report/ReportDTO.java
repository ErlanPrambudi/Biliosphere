package com.example.biliosphere2.dto.report;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:22 PM
@Last Modified 1/30/2025 6:22 PM
Version 1.0
*/


import com.example.biliosphere2.dto.validasi.ValLoginDTO;

import java.util.List;


public class ReportDTO {


    private List<ValLoginDTO> list;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<ValLoginDTO> getList() {
        return list;
    }

    public void setList(List<ValLoginDTO> list) {
        this.list = list;
    }
}
