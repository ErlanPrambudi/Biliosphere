package com.example.biliosphere2.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RespPeminjamanDTO {
    private Long id;
    private RespUserDTO user;
    private RespBukuDTO buku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String statusPengembalian; // ✅ Ubah ke String karena Enum tidak punya ID
    private BigDecimal jumlahDenda;
    private String createdBy;
    private String statusPembayaran;
    private LocalDate createdDate;
    private String updatedBy;
    private LocalDate updatedDate;

    // ✅ Method otomatis hitung keterlambatan
    public boolean isTerlambat() {
        return tanggalKembali != null && tanggalKembali.isAfter(tanggalPinjam.plusDays(7));
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RespUserDTO getUser() {
        return user;
    }

    public void setUser(RespUserDTO user) {
        this.user = user;
    }

    public RespBukuDTO getBuku() {
        return buku;
    }

    public void setBuku(RespBukuDTO buku) {
        this.buku = buku;
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

    public String getStatusPengembalian() { // ✅ Langsung String
        return statusPengembalian;
    }

    public void setStatusPengembalian(String statusPengembalian) { // ✅ Setter juga langsung String
        this.statusPengembalian = statusPengembalian;
    }

    public BigDecimal getJumlahDenda() { // ✅ Getter untuk jumlah denda
        return jumlahDenda;
    }

    public void setJumlahDenda(BigDecimal jumlahDenda) { // ✅ Setter untuk jumlah denda
        this.jumlahDenda = jumlahDenda;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}
