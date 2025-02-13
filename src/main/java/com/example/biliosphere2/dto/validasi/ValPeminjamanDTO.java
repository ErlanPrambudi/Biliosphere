package com.example.biliosphere2.dto.validasi;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// DTO for validating incoming peminjaman requests
public class ValPeminjamanDTO {
    @NotNull(message = "ID User tidak boleh kosong")
    private Long idUser;

    @NotNull(message = "ID Buku tidak boleh kosong")
    private Long idBuku;

    @NotNull(message = "Tanggal pinjam tidak boleh kosong")
    private LocalDate tanggalPinjam;

    private LocalDate tanggalKembali;

    @NotNull(message = "ID Status pengembalian tidak boleh kosong")
    private Long idStatusPengembalian;

    private Long idDenda;
    private String statusPembayaran;

    @AssertTrue(message = "Tanggal kembali harus setelah tanggal pinjam")
    public boolean isTanggalKembaliValid() {
        return tanggalKembali == null || !tanggalKembali.isBefore(tanggalPinjam);
    }

    // Getters and Setters
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(Long idBuku) {
        this.idBuku = idBuku;
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

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Long getIdStatusPengembalian() {
        return idStatusPengembalian;
    }

    public void setIdStatusPengembalian(Long idStatusPengembalian) {
        this.idStatusPengembalian = idStatusPengembalian;
    }

    public Long getIdDenda() {
        return idDenda;
    }

    public void setIdDenda(Long idDenda) {
        this.idDenda = idDenda;
    }
}