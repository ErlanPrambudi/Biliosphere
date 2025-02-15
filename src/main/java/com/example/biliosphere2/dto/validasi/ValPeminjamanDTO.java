package com.example.biliosphere2.dto.validasi;


import com.example.biliosphere2.model.enums.StatusPembayaran;
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

    @NotNull(message = " Status pengembalian tidak boleh kosong")
    private String statusPengembalian;

    private Long idDenda;
    @NotNull
    private StatusPembayaran statusPembayaran;

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

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public String getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(String statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    public Long getIdDenda() {
        return idDenda;
    }

    public void setIdDenda(Long idDenda) {
        this.idDenda = idDenda;
    }
}