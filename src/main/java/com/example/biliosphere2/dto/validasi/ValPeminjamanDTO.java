package com.example.biliosphere2.dto.validasi;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ValPeminjamanDTO {

    @NotNull(message = "User tidak boleh kosong")
    private Long userId;

    @NotNull(message = "Buku tidak boleh kosong")
    private Long bukuId;

    @NotNull(message = "Tanggal pinjam tidak boleh kosong")
    private LocalDate tanggalPinjam;

    @NotNull(message = "Tanggal kembali tidak boleh kosong")
    @Future(message = "Tanggal kembali harus di masa depan")
    private LocalDate tanggalKembali;

    @NotNull(message = "status pengembalian tidak boleh kosong")
    private Long statusPengembalianId;
    // Constructors
    public ValPeminjamanDTO() {}

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBukuId() { return bukuId; }
    public void setBukuId(Long bukuId) { this.bukuId = bukuId; }

    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public void setTanggalPinjam(LocalDate tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }

    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public void setTanggalKembali(LocalDate tanggalKembali) { this.tanggalKembali = tanggalKembali; }

    public Long getStatusPengembalianId() { return statusPengembalianId; }
    public void setStatusPengembalianId(Long statusPengembalianId) { this.statusPengembalianId = statusPengembalianId; }

}