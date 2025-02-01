package com.example.biliosphere2.model;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 10:30 PM
@Last Modified 1/28/2025 10:30 PM
Version 1.0
*/

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstKategori")
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namaKategori", nullable = false, unique = true, length = 50)
    private String namaKategori;

    @OneToMany(mappedBy = "kategori", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Buku> buku;

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

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public List<Buku> getBuku() {
        return buku;
    }

    public void setBuku(List<Buku> buku) {
        this.buku = buku;
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
