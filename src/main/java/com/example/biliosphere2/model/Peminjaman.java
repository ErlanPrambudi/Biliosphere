package com.example.biliosphere2.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 10:27 PM
@Last Modified 1/28/2025 10:27 PM
Version 1.0
*/

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MstPeminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDUser", foreignKey = @ForeignKey(name = "fk-peminjaman-to-user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "IDBuku", foreignKey = @ForeignKey(name = "fk-peminjaman-to-buku"))
    private Buku buku;

    @Column(name = "tanggalPinjam", nullable = false)
    private Date tanggalPinjam;

    @Column(name = "tanggalKembali", nullable = false)
    private Date tanggalKembali;

    @Column(name = "statusPengembalian", nullable = false)
    private Boolean statusPengembalian = false;

    @OneToOne(mappedBy = "peminjaman", cascade = CascadeType.ALL, orphanRemoval = true)
    private Denda denda;  // OneToOne relationship with Denda

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
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

    public Boolean getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(Boolean statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    public Denda getDenda() {
        return denda;
    }

    public void setDenda(Denda denda) {
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