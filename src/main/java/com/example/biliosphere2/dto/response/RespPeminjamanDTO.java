package com.example.biliosphere2.dto.response;


import java.math.BigDecimal;
import java.time.LocalDate;

public class RespPeminjamanDTO {
    private Long id;
    private RespUserDTO user;
    private RespBukuDTO buku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private RespStatusPengembalianDTO statusPengembalian;
    //private RespDendaDTO denda;
    private String createdBy;
    private LocalDate createdDate;
    private String updatedBy;
    private LocalDate updatedDate;
    private BigDecimal jumlahDenda;

    // Method otomatis hitung keterlambatan
    public boolean isTerlambat() {
        return tanggalKembali != null && tanggalKembali.isAfter(tanggalPinjam.plusDays(7));
    }
    // Getters and Setters
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

    public RespStatusPengembalianDTO getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(RespStatusPengembalianDTO statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

//    public RespDendaDTO getDenda() {
//        return denda;
//    }
//
//    public void setDenda(RespDendaDTO denda) {
//        this.denda = denda;
//    }

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