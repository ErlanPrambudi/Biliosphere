package com.example.biliosphere2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/29/2025 12:39 AM
@Last Modified 1/29/2025 12:39 AM
Version 1.0
*/

public class UserDTO {
    private Long id;
    private String nama;

    @JsonProperty("alamat")
    private String alamat;

    @JsonProperty("jenis-kelamin")
    private String jenisKelamin;

    private Date tanggalLahir;

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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}