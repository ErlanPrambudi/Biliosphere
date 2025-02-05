package com.example.biliosphere2.dto.validasi;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/5/2025 3:04 AM
@Last Modified 2/5/2025 3:04 AM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class ValStatusPengembalianDTO {

    @NotBlank(message = "Status tidak boleh kosong")
    @Size(max = 20, message = "Status maksimal 20 karakter")
    private String status;
    private String createdBy;
    private Date createdDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}