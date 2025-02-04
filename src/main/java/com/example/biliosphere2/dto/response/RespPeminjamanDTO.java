package com.example.biliosphere2.dto.response;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:30 PM
@Last Modified 2/4/2025 4:30 PM
Version 1.0
*/

import java.util.Date;

public class RespPeminjamanDTO {
    private Long id;
    private Long userId;
    private String userName;  // tambahan untuk menampilkan nama user
    private Long bukuId;
    private String judulBuku; // tambahan untuk menampilkan judul buku
    private Date tanggalPinjam;
    private Date tanggalKembali;
    private Boolean statusPengembalian;
    private RespDendaDTO denda;  // nested DTO untuk informasi denda
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    // constructor
    public RespPeminjamanDTO() {
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getBukuId() {
        return bukuId;
    }

    public void setBukuId(Long bukuId) {
        this.bukuId = bukuId;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Boolean statusPengembalian() {
        return statusPengembalian;
    }

    public void statusPengembalian(Boolean statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    public RespDendaDTO getDenda() {
        return denda;
    }

    public void setDenda(RespDendaDTO denda) {
        this.denda = denda;
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
}