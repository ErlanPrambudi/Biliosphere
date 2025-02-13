package com.example.biliosphere2.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 10:32 PM
@Last Modified 1/28/2025 10:32 PM
Version 1.0
*/

import com.example.biliosphere2.model.enums.StatusPembayaran;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "MstDenda")
public class Denda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "peminjaman_id", unique = true) // Unik, jadi satu peminjaman cuma punya satu denda
    private Peminjaman peminjaman;

    @Column(name = "jumlahDenda", nullable = false)
    private BigDecimal jumlahDenda;

    @Column(name = "status_pembayaran", nullable = false) // Nama kolom di DB
    @Enumerated(EnumType.STRING) // Simpan sebagai String di DB
    private StatusPembayaran statusPembayaran; // "belum dibayar", "dibayar"


    @Column(name = "tanggalDenda", nullable = false)
    private LocalDate tanggalDenda;

    @CreationTimestamp
    @Column(name = "createdDate", updatable = false, nullable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "updatedDate")
    private LocalDate updatedDate;

    @Column(name = "createdBy", nullable = false)
    private String createdBy;

    @Column(name = "updatedBy")
    private String updatedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

    public BigDecimal  getJumlahDenda() {
        return jumlahDenda;
    }

    public void setJumlahDenda(BigDecimal  jumlahDenda) {
        this.jumlahDenda = jumlahDenda;
    }

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public LocalDate getTanggalDenda() {
        return tanggalDenda;
    }

    public void setTanggalDenda(LocalDate tanggalDenda) {
        this.tanggalDenda = tanggalDenda;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }



    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

