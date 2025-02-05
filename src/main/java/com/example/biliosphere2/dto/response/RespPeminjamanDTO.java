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

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RespPeminjamanDTO {
    private Long id;
    private Long userId;
    private Long bukuId;
    private String judulBuku; // tambahan untuk menampilkan judul buku
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String statusPengembalianNama;
    private Long dendaId;  // nested DTO untuk informasi denda
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

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

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public String getStatusPengembalianNama() {
        return statusPengembalianNama;
    }

    public void setStatusPengembalianNama(String statusPengembalianNama) {
        this.statusPengembalianNama = statusPengembalianNama;
    }

    public Long getDendaId() {
        return dendaId;
    }

    public void setDendaId(Long dendaId) {
        this.dendaId = dendaId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
