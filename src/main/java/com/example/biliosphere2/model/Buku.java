package com.example.biliosphere2.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 10:26 PM
@Last Modified 1/28/2025 10:26 PM
Version 1.0
*/

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstBuku")
public class Buku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "judul", nullable = false, length = 255)
    private String judul;

    @Column(name = "penulis", nullable = false, length = 100)
    private String penulis;

    @Column(name = "penerbit", nullable = false, length = 100)
    private String penerbit;

    @Column(name = "tahunTerbit")
    private Integer tahunTerbit;

    @Column(name = "harga", nullable = false, precision = 10, scale = 2)
    private BigDecimal harga;

    @ManyToOne
    @JoinColumn(name = "IDKategori", foreignKey = @ForeignKey(name = "fk-buku-to-kategori"))
    private Kategori kategori;

    @Column(name = "stok", nullable = false)
    private Integer stok;

    @OneToMany(mappedBy = "buku", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Peminjaman> peminjaman;

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

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public List<Peminjaman> getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(List<Peminjaman> peminjaman) {
        this.peminjaman = peminjaman;
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
