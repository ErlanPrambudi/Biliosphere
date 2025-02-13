package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/5/2025 3:04 AM
@Last Modified 2/5/2025 3:04 AM
Version 1.0
*/

import java.util.Date;
public class RespStatusPengembalianDTO {
    private Long id;
    private String status;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    // Constructor kosong (wajib buat Spring & ModelMapper)
    public RespStatusPengembalianDTO() {}

    // Constructor dengan 2 parameter
    public RespStatusPengembalianDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}