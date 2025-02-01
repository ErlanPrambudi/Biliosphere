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

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MstDenda")
public class Denda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "IDPeminjaman", foreignKey = @ForeignKey(name = "fk-denda-to-peminjaman"))
    private Peminjaman peminjaman;  // OneToOne relationship with Peminjaman

    @Column(name = "jumlahDenda", nullable = false)
    private Double jumlahDenda;

    @Column(name = "tanggalDenda", nullable = false)
    private Date tanggalDenda = new Date();

    @Column(name = "statusPembayaran", nullable = false)
    private Boolean statusPembayaran = false;

    @Column(name = "createdBy", updatable = false, nullable = false)
    private String createdBy;

    @Column(name = "createdDate", updatable = false, nullable = false)
    private Date createdDate = new Date();

    @Column(name = "updatedBy", insertable = false)
    private String updatedBy;

    @Column(name = "updatedDate", insertable = false)
    private Date updatedDate;

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

    public Double getJumlahDenda() {
        return jumlahDenda;
    }

    public void setJumlahDenda(Double jumlahDenda) {
        this.jumlahDenda = jumlahDenda;
    }

    public Date getTanggalDenda() {
        return tanggalDenda;
    }

    public void setTanggalDenda(Date tanggalDenda) {
        this.tanggalDenda = tanggalDenda;
    }

    public Boolean getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(Boolean statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
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