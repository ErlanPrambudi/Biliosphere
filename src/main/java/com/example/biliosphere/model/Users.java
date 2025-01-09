package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 2:40 AM
@Last Modified 1/8/2025 2:40 AM
Version 1.0
*/
@Entity
@Table(name = "MstUsers")
public class Users {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name",nullable = false)
    private String nama;

    @Column(name = "Address",nullable = false,length = 255)
    private String alamat;

    @Column(name = "PhoneNumber ",nullable = false,length = 13)
    private String noTelpon;

    @Column(name = "Email",nullable = false,length = 50)
    private String email;

    @Column(name = "RegistrationDate",nullable = false)
    private LocalDate tanggalDaftar;

    @PrePersist
    protected void onCreate() {
        this.tanggalDaftar = LocalDate.now();
    }

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

    public String getNoTelpon() {
        return noTelpon;
    }

    public void setNoTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
}
