package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/29/2025 11:46 PM
@Last Modified 1/29/2025 11:46 PM
Version 1.0
*/



import com.example.biliosphere2.model.Akses;
import jakarta.persistence.*;

import java.time.LocalDate;

public class RespUserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String alamat;
    private String noHp;
    private RespAksesDTO akses;
    private LocalDate tanggalLahir;
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public RespAksesDTO getAkses() {
        return akses;
    }

    public void setAkses(RespAksesDTO akses) {
        this.akses = akses;
    }
}