package com.example.biliosphere2.dto.validasi;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:29 PM
@Last Modified 2/4/2025 4:29 PM
Version 1.0
*/

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.util.Date;

public class ValPeminjamanDTO {
    @NotNull(message = "ID User tidak boleh kosong")
    private Long userId;

    @NotNull(message = "ID Buku tidak boleh kosong")
    private Long bukuId;

    @NotNull(message = "Tanggal pinjam tidak boleh kosong")
    @FutureOrPresent(message = "Tanggal pinjam tidak boleh di masa lalu")
    private Date tanggalPinjam;

    @NotNull(message = "Tanggal kembali tidak boleh kosong")
    @FutureOrPresent(message = "Tanggal kembali tidak boleh di masa lalu")
    private Date tanggalKembali;

    @NotNull(message = "Status peminjaman tidak boleh kosong")
    private Boolean statusPengembalian;

    public Boolean getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(Boolean statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    // constructor
    public ValPeminjamanDTO() {
    }

    // getters and setters
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
}
